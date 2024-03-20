package com.marksilva.fileparser.backendspringboot.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("metadataFile")
public class MetadataFile {
    @Id
    private ObjectId id;
    private ObjectId parsedFileId;
    private String localPathToOrgFile;
    private LocalDate dateFileParsed;

    public MetadataFile(){
    }

    public MetadataFile(ObjectId id, ObjectId parsedFileId, String localPathToOrgFile, LocalDate dateFileParsed) {
        this.id = id;
        this.parsedFileId = parsedFileId;
        this.localPathToOrgFile = localPathToOrgFile;
        this.dateFileParsed = dateFileParsed;
    }

    public MetadataFile(ObjectId parsedFileId, LocalDate dateFileParsed) {
        this.parsedFileId = parsedFileId;
        this.dateFileParsed = dateFileParsed;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getParsedFileId() {
        return parsedFileId;
    }

    public void setParsedFileId(ObjectId parsedFileId) {
        this.parsedFileId = parsedFileId;
    }

    public LocalDate getDateFileParsed() {
        return dateFileParsed;
    }

    public void setDateFileParsed(LocalDate dateFileParsed) {
        this.dateFileParsed = dateFileParsed;
    }

    public String getLocalPathToOrgFile() {
        return localPathToOrgFile;
    }

    public void setLocalPathToOrgFile(String localPathToOrgFile) {
        this.localPathToOrgFile = localPathToOrgFile;
    }
}
