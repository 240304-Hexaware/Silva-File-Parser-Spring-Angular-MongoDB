package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.DuplicateUsernameException;
import com.marksilva.fileparser.backendspringboot.exceptions.InvalidInputException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(ObjectId id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("User with id - " + id + " - could not be found "));
    }

    //TODO: Use a UserDTO instead of regular user where password is not sent back to the user.
    // Maybe include token as well
    public User findByUsername(String username) throws UserNotFoundException{
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UserNotFoundException("User with username - " + username + " - could not be found"));
    }

    public User registerUser(User newUser) throws DuplicateUsernameException, InvalidInputException {
        if(newUser.getUsername() == null || newUser.getUsername().isEmpty()) {
            throw new InvalidInputException("Username cannot be blank or null");
        }
        if(newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            throw new InvalidInputException("Password cannot be blank or null");
        }
        if(userRepository.existsUserByUsername(newUser.getUsername())) {
            throw new DuplicateUsernameException("User with username - " + newUser.getUsername() + " - already exists");
        }
        return userRepository.save(new User(newUser.getUsername(), newUser.getPassword()));
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
}
