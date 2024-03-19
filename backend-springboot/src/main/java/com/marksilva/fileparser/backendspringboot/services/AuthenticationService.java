package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.DuplicateUsernameException;
import com.marksilva.fileparser.backendspringboot.models.Role;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.RoleRepository;
import com.marksilva.fileparser.backendspringboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional  //Make Spring manage transaction.
public class AuthenticationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User newUser) throws DuplicateUsernameException {
        if(userRepository.existsUserByUsername(newUser.getUsername())) {
            throw new DuplicateUsernameException("User with username - " + newUser.getUsername() + " - already exists");
        }
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        //TODO: Create Expception Case for ROLE not found
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return userRepository.save(new User(newUser.getUsername(), encodedPassword, authorities));
    }

}
