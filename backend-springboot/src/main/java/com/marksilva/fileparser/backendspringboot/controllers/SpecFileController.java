package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidSpecFileException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.services.SpecFileService;
import com.marksilva.fileparser.backendspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users/{username}/specFile")
@CrossOrigin(origins = "*")
public class SpecFileController {
    private SpecFileService specFileService;
    private UserService userService;

    @Autowired
    public SpecFileController(SpecFileService specFileService, UserService userService) {
        this.specFileService = specFileService;
        this.userService = userService;
    }

    /**
     * Get a SpecFile using the given specFileName
     * @param specFileName the specFileName of the SpecFile being searched for
     * @return the Specfile of the given specFileName
     * @throws SpecFileNotFoundException when the SpecFile with the given specFileName could not be found
     */
    @GetMapping("{specFileName}")
    @ResponseStatus(HttpStatus.OK)
    public SpecFile getByName(@PathVariable String specFileName) throws SpecFileNotFoundException {
        return specFileService.findByName(specFileName);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<SpecFile> getByUsername(@PathVariable String username) throws UserNotFoundException {
        User currUser = this.userService.findByUsername(username);
        return this.specFileService.findListOfSpecFile(currUser);
    }

    /**
     * Post a new SpecFile using a json representation of a specfile and the userID of the user who
     * uploaded the specFile.
     * @param specFileAsJson The Json representation of the specfile to be stored into the database
     * @param username the username of the user who uploaded the specfile
     * @return The specFile uploaded to the database with their unique ObjectID
     * @throws InvalidSpecFileException when SpecFile does not have a name or docOfFields.
     * @throws IOException
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SpecFile postNewSpecFile(@RequestBody MultipartFile specFileAsJson, @PathVariable String username) throws InvalidSpecFileException, IOException, UserNotFoundException {
        //TODO: Replace UserID with JWT Token
        User userPoster = this.userService.findByUsername(username);
        SpecFile newSpecFile = this.specFileService.insertNewSpecFile(specFileAsJson, userPoster.getId());
        this.userService.addSpecFileId(newSpecFile.getId(), userPoster);
        return newSpecFile;
    }
}
