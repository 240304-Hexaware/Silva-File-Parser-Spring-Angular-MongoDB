package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.repositories.ParsedFileRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ParsedFileService {
    private ParsedFileRepository parsedFileRepository;

    @Autowired
    public ParsedFileService(ParsedFileRepository parsedFileRepository){
        this.parsedFileRepository = parsedFileRepository;
    }

    //TODO: ADD USER ID AND METADATA ID
    public ParsedFile insertFile(MultipartFile flatFile, SpecFile specFile) throws IOException {
        ParsedFile newParsedFile = new ParsedFile();

        Document docOfKeyAndValue = new Document();

        String fileContent = this.parseFileToString(flatFile);
        Document specFileDoc = specFile.getDocOfFields();
        for(String fieldName : specFileDoc.keySet()){
            //TODO: Add Class Types
            Document fieldDoc = specFileDoc.get(fieldName, Document.class);
            String fieldValue = fileContent.substring(
                    fieldDoc.getInteger("start_pos"), fieldDoc.getInteger("end_pos") + 1)
                    .trim();
            docOfKeyAndValue.put(fieldName, fieldValue);
        }

        newParsedFile.setFileInfo(docOfKeyAndValue);
        return this.parsedFileRepository.save(newParsedFile);
    }

    public String parseFileToString(MultipartFile flatFile) throws IOException {
        InputStreamReader reader = new InputStreamReader(flatFile.getInputStream());
        StringBuilder builder = new StringBuilder();
        while(reader.ready()) {
            builder.append((char) reader.read());
        }
        return builder.toString();
    }
}
