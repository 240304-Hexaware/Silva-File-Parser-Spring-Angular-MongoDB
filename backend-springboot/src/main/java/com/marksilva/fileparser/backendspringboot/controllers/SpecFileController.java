package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.services.SpecFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpecFileController {
    private SpecFileService specFileService;

    @Autowired
    public SpecFileController(SpecFileService specFileService) {
        this.specFileService = specFileService;
    }

    @GetMapping("/specFile/{name}")
    @ResponseStatus(HttpStatus.OK)
    public SpecFile getByName(@PathVariable String name) throws SpecFileNotFoundException {
        return specFileService.findByName(name);
    }

    @ExceptionHandler(SpecFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String specFileNotFound(SpecFileNotFoundException e){
        return e.getMessage();
    }
}
