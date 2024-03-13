package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.DuplicateUsernameException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUser(User newUser) throws DuplicateUsernameException {
        if(userRepository.existsUserByUsername(newUser.getUsername())) {
            throw new DuplicateUsernameException("User with username - " + newUser.getUsername() + " - already exists");
        }
        return userRepository.save(newUser);
    }

    public User findById(ObjectId id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id - " + id + " - could not be found "));
    }

    public User findByUsername(String username) throws UserNotFoundException{
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with username - " + username + " - could not be found"));
    }
}
