package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidSpecFileException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.repositories.SpecFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecFileService {
    private SpecFileRepository specFileRepository;

    @Autowired
    public SpecFileService(SpecFileRepository specFileRepository){
        this.specFileRepository = specFileRepository;
    }

    public SpecFile findByName(String name) throws SpecFileNotFoundException {
        return specFileRepository.findByName(name).orElseThrow(() ->
                new SpecFileNotFoundException("Spec File with name - " + name + " - could not be found" ));
    }

    public SpecFile insertNewSpecFile(SpecFile newSpecFile) throws InvalidSpecFileException {
        if(newSpecFile.getName() == null) {
            throw new InvalidSpecFileException("The specFile must have a 'name' field that is not null");
        }
        if(newSpecFile.getDocOfFields() == null) {
            throw new InvalidSpecFileException("The specFile must have a 'listOfFields' field that is not null");
        }
        return specFileRepository.save(newSpecFile);
    }
}