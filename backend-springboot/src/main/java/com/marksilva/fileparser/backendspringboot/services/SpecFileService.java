package com.marksilva.fileparser.backendspringboot.services;

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
}
