package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User findById(ObjectId id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id - " + id + " - could not be found "));
    }

    public User findByUsername(String username) throws UserNotFoundException{
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with username - " + username + " - could not be found"));
    }

    public User addSpecFileIdWithUserId(ObjectId specFileId, String userId) throws UserNotFoundException {
        //TODO: Exception Handling
        User userToUpdate = this.findById(new ObjectId(userId));
        userToUpdate.getListOfSpecFileIds().add(specFileId);
        return this.userRepository.save(userToUpdate);
    }

    public User addSpecFileId(ObjectId specFileId, User user){
        //TODO: Exception Handling
        user.getListOfSpecFileIds().add(specFileId);
        return this.userRepository.save(user);
    }

    public User addParsedFileId(ObjectId parsedFileId, User user){
        user.getListOfParsedFileIds().add(parsedFileId);
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username - " + username + " - could not be found"));
    }
}
