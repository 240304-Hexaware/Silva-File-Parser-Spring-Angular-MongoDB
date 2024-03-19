package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.DuplicateUsernameException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Test Connection to the Spring Server from API call
     * @return Pong within the response body
     */
    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    /**
     * Get the user with the given ID
     * @param id The id of the user being searched for within the database
     * @return The User with the given id
     * @throws UserNotFoundException - User with the given Id could not be found
     */
    @GetMapping("/users/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getById(@PathVariable String id) throws UserNotFoundException {
        return userService.findById(new ObjectId(id));
    }

    /**
     * Get the user with the given user name
     * @param username the username of the user being searched for
     * @return The user with the given username
     * @throws UserNotFoundException - User with the given username could not be found
     */
    @GetMapping("/users/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getByUsername(@PathVariable String username) throws UserNotFoundException {
        return userService.findByUsername(username);
    }
}
