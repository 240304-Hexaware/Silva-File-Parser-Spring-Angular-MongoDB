package com.marksilva.fileparser.backendspringboot.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marksilva.fileparser.backendspringboot.exceptions.InvalidSpecFileException;
import com.marksilva.fileparser.backendspringboot.exceptions.SpecFileNotFoundException;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.repositories.SpecFileRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class SpecFileService {
    private SpecFileRepository specFileRepository;

    @Autowired
    public SpecFileService(SpecFileRepository specFileRepository){
        this.specFileRepository = specFileRepository;
    }

    /**
     * Find a SpecFile by the name of the SpecFile
     * @param name the name of the SpecFile being searched for
     * @return The SpecFile of the given name
     * @throws SpecFileNotFoundException when a specfile of the given name is not found
     */
    public SpecFile findByName(String name) throws SpecFileNotFoundException {
        return specFileRepository.findByName(name).orElseThrow(() ->
                new SpecFileNotFoundException("Spec File with name - " + name + " - could not be found" ));
    }

    /**
     * Insert a new specfile given a MultipartFile representation of a json file and a String represention of the
     * ObjectID of the user who uploaded the specFile.
     * @param specFileAsJson The MultipartFile representation of the specfile
     * @param userID the userID of who uploaded the specFile
     * @return The specFile with the objectID
     * @throws InvalidSpecFileException when specFile does not have a name or DocOfFields
     * @throws IOException
     */
    public SpecFile insertNewSpecFile(MultipartFile specFileAsJson, String userID) throws InvalidSpecFileException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String specFileContent = HelperForService.parseFileToString(specFileAsJson);

        SpecFile newSpecFile = objectMapper.readValue(specFileContent, SpecFile.class);
        newSpecFile.setCreatedByUserId(new ObjectId(userID));

        //TODO: Validate that the specFile was added by a user
        if(newSpecFile.getName() == null) {
            throw new InvalidSpecFileException("The specFile must have a 'name' field that is not null");
        }
        if(newSpecFile.getDocOfFields() == null) {
            throw new InvalidSpecFileException("The specFile must have a 'listOfFields' field that is not null");
        }
        return specFileRepository.save(newSpecFile);
    }
}