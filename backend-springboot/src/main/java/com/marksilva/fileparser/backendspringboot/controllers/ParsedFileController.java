package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.services.ParsedFileService;
import com.marksilva.fileparser.backendspringboot.services.SpecFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ParsedFileController {
    private ParsedFileService parsedFileService;
    private SpecFileService specFileService;

    @Autowired
    public ParsedFileController(ParsedFileService parsedFileService, SpecFileService specFileService){
        this.parsedFileService = parsedFileService;
        this.specFileService = specFileService;
    }

    @PostMapping("/file/specFile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ParsedFile postNewParsedFileWithSpecFileName(@RequestParam MultipartFile flatFile, @RequestParam String specFileName) throws SpecFileNotFoundException, IOException {
        SpecFile specFile = this.specFileService.findByName(specFileName);
        return this.parsedFileService.insertFile(flatFile, specFile);
    }

    @ExceptionHandler(SpecFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String specFileNotFound(SpecFileNotFoundException e){
        return e.getMessage();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String IOException(IOException e){
        return e.getMessage();
    }
}
