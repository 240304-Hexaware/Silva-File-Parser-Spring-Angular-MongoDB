package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidInputException;
import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.ParsedFileRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParsedFileService {
    private ParsedFileRepository parsedFileRepository;

    @Autowired
    public ParsedFileService(ParsedFileRepository parsedFileRepository) {
        this.parsedFileRepository = parsedFileRepository;
    }

    //TODO: ADD METADATA ID AND STORE FLAT FILE TO BLOCK STORAGE
    public List<ParsedFile> insertFile(MultipartFile flatFile, String flatFileName, SpecFile specFile, User user) throws IOException, InvalidInputException {
        //TODO: Create New EXCEPTION if wanted
        //User Cannot use specfile that it did not create
        if (!user.getListOfSpecFileIds().contains(specFile.getId())) {
            throw new InvalidInputException(String.format("Cannot find specFile: %s in the user: %s", specFile.getName(), user.getUsername()));
        }

        List<ParsedFile> listOfParsedFiles = new ArrayList<>();
        String fileContent = HelperForService.parseFileToString(flatFile);
        Document specFileDoc = specFile.getDocOfFields();

        for (String strLine : fileContent.split("\\r?\\n")) {
            ParsedFile newParsedFile = new ParsedFile();
            Document docParsedFileInfo = new Document();
            for (String fieldName : specFileDoc.keySet()) {
                //TODO: Add Class Types
                Document fieldDoc = specFileDoc.get(fieldName, Document.class);
                String fieldValue = strLine.substring(
                                fieldDoc.getInteger("start_pos"), fieldDoc.getInteger("end_pos") + 1)
                        .trim();
                docParsedFileInfo.put(fieldName, fieldValue);
            }

            newParsedFile.setUserId(user.getId());
            newParsedFile.setSpecId(specFile.getId());
            newParsedFile.setFileInfo(docParsedFileInfo);

            listOfParsedFiles.add(newParsedFile);
        }
        //TODO: Replace with Non-Local save
        HelperForService.uploadFileLocally(fileContent, "src\\main\\resources\\flatFileStorage\\" + user.getUsername(), flatFileName + ".txt");

        return this.parsedFileRepository.saveAll(listOfParsedFiles);
    }

    public List<ParsedFile> findParsedFilesByUserId(ObjectId userID) {
        return this.parsedFileRepository.findByUserId(userID);
    }
}
