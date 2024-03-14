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

    @GetMapping("/ping")
    public String ping() {
        return "pong!";
    }

    @GetMapping("/users/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getById(@PathVariable String id) throws UserNotFoundException {
        return userService.findById(new ObjectId(id));
    }

    @GetMapping("/users/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public User getByUsername(@PathVariable String username) throws UserNotFoundException {
        return userService.findByUsername(username);
    }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User registerNewUser(@RequestBody User newUser) throws DuplicateUsernameException {
        return this.userService.registerNewUser(newUser);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFound(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateUserFound(DuplicateUsernameException e) {
        return e.getMessage();
    }
}
