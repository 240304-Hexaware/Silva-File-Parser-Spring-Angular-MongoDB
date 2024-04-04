package com.marksilva.fileparser.backendspringboot.services;

import com.marksilva.fileparser.backendspringboot.exceptions.InvalidInputException;
import com.marksilva.fileparser.backendspringboot.models.ParsedFile;
import com.marksilva.fileparser.backendspringboot.models.SpecFile;
import com.marksilva.fileparser.backendspringboot.models.User;
import com.marksilva.fileparser.backendspringboot.repositories.ParsedFileRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AccumulatorOperators;
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

    //TODO: ADD METADATA ID
    public List<ParsedFile> insertFile(MultipartFile flatFile, String flatFileName, SpecFile specFile, User user) throws IOException, InvalidInputException {
        //TODO: Create New EXCEPTION if wanted
        //User Cannot use specfile that it did not create
        if (!user.getListOfSpecFileIds().contains(specFile.getId())) {
            throw new InvalidInputException(String.format("Cannot find specFile: %s in the user: %s", specFile.getName(), user.getUsername()));
        }

        List<ParsedFile> listOfParsedFiles = new ArrayList<>();
        String fileContent = HelperForService.parseFileToString(flatFile);
        Document specFileDoc = specFile.getDocOfFields();

        int specFileMaxLineLength = 0;
        int flatFileRecordLength = 0;

        //Multi line record reading
        for (String strLine : fileContent.split("\\r?\\n")) {
            flatFileRecordLength = strLine.length();
            ParsedFile newParsedFile = new ParsedFile();
            Document docParsedFileInfo = new Document();

            // For each line create a new doc using specFile
            for (String fieldName : specFileDoc.keySet()) {
                //TODO: Add Class Types
                Document fieldDoc = specFileDoc.get(fieldName, Document.class);
                try{
                    String fieldValue = strLine.substring(
                                fieldDoc.getInteger("start_pos"), fieldDoc.getInteger("end_pos") + 1)
                        .trim();
                    docParsedFileInfo.put(fieldName, fieldValue);
                    specFileMaxLineLength = Math.max(specFileMaxLineLength, fieldDoc.getInteger("end_pos") + 1);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new InvalidInputException("Spec File Out of bounds reading Flat File");
                }
            }

            newParsedFile.setUserId(user.getId());
            newParsedFile.setSpecId(specFile.getId());
            newParsedFile.setFileInfo(docParsedFileInfo);

            listOfParsedFiles.add(newParsedFile);
        }

        if(flatFileRecordLength > specFileMaxLineLength) {
            StringBuilder errorMsg = new StringBuilder();
//            errorMsg.append("Flat File Record Length: ").append(flatFileRecordLength).append(" characters\n");
//            errorMsg.append("Spec File Record Length: ").append(specFileMaxLineLength).append(" characters\n");
            errorMsg.append("Cannot read Flat File with longer record length than Spec File max record length");
            throw new InvalidInputException(errorMsg.toString());
        }
        //TODO: Replace with Non-Local save
        HelperForService.uploadFileLocally(fileContent, "src\\main\\resources\\flatFileStorage\\" + user.getUsername(), flatFileName);

        return this.parsedFileRepository.saveAll(listOfParsedFiles);
    }

    public List<ParsedFile> findParsedFilesByUserId(ObjectId userID) {
        return this.parsedFileRepository.findByUserId(userID);
    }

    public List<ParsedFile> findParsedFilesByUserIdAndSpecFileId(ObjectId userID, ObjectId specFileID) {
        return this.parsedFileRepository.findByUserIdAndSpecId(userID, specFileID);
    }
}
