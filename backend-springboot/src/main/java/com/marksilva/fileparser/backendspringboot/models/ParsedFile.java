package com.marksilva.fileparser.backendspringboot.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("parsedFiles")
public class ParsedFile {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private ObjectId specId;
    private ObjectId metaDataId;
    private org.bson.Document fileInfo;

    public ParsedFile() {
    }

    public ParsedFile(ObjectId id, ObjectId userId, ObjectId specId, ObjectId metaDataId, org.bson.Document fileInfo) {
        this.id = id;
        this.userId = userId;
        this.specId = specId;
        this.metaDataId = metaDataId;
        this.fileInfo = fileInfo;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getSpecId() {
        return specId;
    }

    public void setSpecId(ObjectId specId) {
        this.specId = specId;
    }

    public ObjectId getMetaDataId() {
        return metaDataId;
    }

    public void setMetaDataId(ObjectId metaDataId) {
        this.metaDataId = metaDataId;
    }

    public org.bson.Document getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(org.bson.Document fileInfo) {
        this.fileInfo = fileInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParsedFile that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getMetaDataId(), that.getMetaDataId()) && Objects.equals(getFileInfo(), that.getFileInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getMetaDataId(), getFileInfo());
    }

    @Override
    public String toString() {
        return "ParsedFile{" +
                "id=" + id +
                ", userId=" + userId +
                ", metaDataId=" + metaDataId +
                ", fileInfo=" + fileInfo +
                '}';
    }
}
