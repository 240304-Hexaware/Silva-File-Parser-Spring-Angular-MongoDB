package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidSpecFileException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.services.SpecFileService;
import com.marksilva.fileparser.backendspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users/{userID}/specFile")
public class SpecFileController {
    private SpecFileService specFileService;
    private UserService userService;

    @Autowired
    public SpecFileController(SpecFileService specFileService, UserService userService) {
        this.specFileService = specFileService;
        this.userService = userService;
    }

    /**
     * Get a SpecFile using the given name
     * @param name the name of the SpecFile being searched for
     * @return the Specfile of the given name
     * @throws SpecFileNotFoundException when the SpecFile with the given name could not be found
     */
    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public SpecFile getByName(@PathVariable String name) throws SpecFileNotFoundException {
        return specFileService.findByName(name);
    }

    /**
     * Post a new SpecFile using a json representation of a specfile and the userID of the user who
     * uploaded the specFile.
     * @param specFileAsJson The Json representation of the specfile to be stored into the database
     * @param userID the userId of the user who uploaded the specfile
     * @return The specFile uploaded to the database with their unique ObjectID
     * @throws InvalidSpecFileException when SpecFile does not have a name or docOfFields.
     * @throws IOException
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SpecFile postNewSpecFile(@RequestBody MultipartFile specFileAsJson, @PathVariable String userID) throws InvalidSpecFileException, IOException, UserNotFoundException {
        //TODO: Replace UserID with JWT Token
        SpecFile newSpecFile = this.specFileService.insertNewSpecFile(specFileAsJson, userID);
        this.userService.addSpecFileIdToUser(newSpecFile.getId(), userID);
        return newSpecFile;
    }

    @ExceptionHandler(SpecFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String specFileNotFound(SpecFileNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(InvalidSpecFileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidSpecFile(InvalidSpecFileException e) {
        return e.getMessage();
    }
}
