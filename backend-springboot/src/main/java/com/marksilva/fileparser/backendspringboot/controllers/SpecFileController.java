package com.marksilva.fileparser.backendspringboot.controllers;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidSpecFileException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.services.SpecFileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/specFile")
public class SpecFileController {
    private SpecFileService specFileService;

    @Autowired
    public SpecFileController(SpecFileService specFileService) {
        this.specFileService = specFileService;
    }

    @GetMapping("{name}")
    @ResponseStatus(HttpStatus.OK)
    public SpecFile getByName(@PathVariable String name) throws SpecFileNotFoundException {
        return specFileService.findByName(name);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SpecFile postNewSpecFile(@RequestBody SpecFile newSpecFile) throws InvalidSpecFileException {
        return this.specFileService.insertNewSpecFile(newSpecFile);
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
