package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidInputException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.services.ParsedFileService;
import com.marksilva.fileparser.backendspringboot.services.SpecFileService;
import com.marksilva.fileparser.backendspringboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users/{username}/file")
public class ParsedFileController {
    private ParsedFileService parsedFileService;
    private SpecFileService specFileService;
    private UserService userService;

    @Autowired
    public ParsedFileController(ParsedFileService parsedFileService, SpecFileService specFileService, UserService userService){
        this.parsedFileService = parsedFileService;
        this.specFileService = specFileService;
        this.userService = userService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ParsedFile> getParsedFilesByUserId(@PathVariable String username) throws UserNotFoundException {
        return this.parsedFileService.findParsedFilesByUserId(this.userService.findByUsername(username).getId());
    }

    @PostMapping("specFile/{specFileName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ParsedFile postNewParsedFileWithSpecFileName(@RequestParam MultipartFile flatFile, @PathVariable String specFileName, @PathVariable String username) throws SpecFileNotFoundException, IOException, UserNotFoundException, InvalidInputException {
        User currUser = this.userService.findByUsername(username);
        SpecFile specFile = this.specFileService.findByName(specFileName);
        return this.parsedFileService.insertFile(flatFile, specFile, currUser);
    }
}
