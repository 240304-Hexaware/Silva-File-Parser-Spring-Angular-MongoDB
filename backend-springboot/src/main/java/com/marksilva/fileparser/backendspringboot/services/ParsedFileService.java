package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.repositories.ParsedFileRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ParsedFileService {
    private ParsedFileRepository parsedFileRepository;

    @Autowired
    public ParsedFileService(ParsedFileRepository parsedFileRepository){
        this.parsedFileRepository = parsedFileRepository;
    }

    //TODO: ADD METADATA ID AND STORE FLAT FILE TO BLOCK STORAGE
    public ParsedFile insertFile(MultipartFile flatFile, SpecFile specFile, String userID) throws IOException {
        ParsedFile newParsedFile = new ParsedFile();
        Document docParsedFileInfo = new Document();

        String fileContent = HelperForService.parseFileToString(flatFile);
        Document specFileDoc = specFile.getDocOfFields();
        for(String fieldName : specFileDoc.keySet()){
            //TODO: Add Class Types
            Document fieldDoc = specFileDoc.get(fieldName, Document.class);
            String fieldValue = fileContent.substring(
                    fieldDoc.getInteger("start_pos"), fieldDoc.getInteger("end_pos") + 1)
                    .trim();
            docParsedFileInfo.put(fieldName, fieldValue);
        }

        newParsedFile.setFileInfo(docParsedFileInfo);
        newParsedFile.setUserId(new ObjectId(userID));
        return this.parsedFileRepository.save(newParsedFile);
    }

    public List<ParsedFile> findParsedFilesByUserId(String userID){
        return this.parsedFileRepository.findByUserId(new ObjectId((userID)));
    }
}
