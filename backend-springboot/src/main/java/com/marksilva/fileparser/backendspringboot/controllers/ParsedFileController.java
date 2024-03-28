package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidInputException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.exceptions.UserNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.services.MetadataFileService;
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
@CrossOrigin(origins = "*")
public class ParsedFileController {
    private ParsedFileService parsedFileService;
    private SpecFileService specFileService;
    private UserService userService;
    private MetadataFileService metadataFileService;

    @Autowired
    public ParsedFileController(ParsedFileService parsedFileService, SpecFileService specFileService, UserService userService, MetadataFileService metadataFileService) {
        this.parsedFileService = parsedFileService;
        this.specFileService = specFileService;
        this.userService = userService;
        this.metadataFileService = metadataFileService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ParsedFile> getParsedFilesByUserId(@PathVariable String username) throws UserNotFoundException {
        return this.parsedFileService.findParsedFilesByUserId(this.userService.findByUsername(username).getId());
    }

    @PostMapping("specFile/{specFileName}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ParsedFile> postNewParsedFileWithSpecFileName(@RequestParam MultipartFile flatFile, @RequestParam String flatFileName, @PathVariable String specFileName, @PathVariable String username) throws SpecFileNotFoundException, IOException, UserNotFoundException, InvalidInputException {
        // TODO: Can reduce queries if we use Object ID instead of just the names
        User currUser = this.userService.findByUsername(username);
        SpecFile specFile = this.specFileService.findByName(specFileName);
        List<ParsedFile> listOfParsedFiles = this.parsedFileService.insertFile(flatFile, flatFileName, specFile, currUser);

        // Add ParsedFile IDs to User who uploaded the files
        this.userService.addListOfParsedFileId(listOfParsedFiles, currUser);
        // TODO: Find a way to update MetadataID field in parsedFile
        // Insert MetaData File related to each ParsedFile
        this.metadataFileService.insertListOfMetadataFileLocal(listOfParsedFiles, "src\\main\\java\\resources\\" + currUser.getUsername() + "\\" + flatFileName + ".txt");

        return listOfParsedFiles;
    }
}
