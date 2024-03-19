package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.DuplicateUsernameException;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")   //TODO: Study more about this
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User newUser) throws DuplicateUsernameException {
        return this.authenticationService.registerUser(newUser);
    }

    /**
     * Handle Error when User tries to create an account with an already existing username
     * @param e The DuplicateUsername Exception thrown
     * @return the message of the exception
     */
    @ExceptionHandler(DuplicateUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String duplicateUserFound(DuplicateUsernameException e) {
        return e.getMessage();
    }
}
