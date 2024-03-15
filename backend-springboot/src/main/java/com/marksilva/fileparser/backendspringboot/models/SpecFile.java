package com.marksilva.fileparser.backendspringboot.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.Objects;

@Document("specFiles")
public class SpecFile {
    @Id
    private ObjectId id;
    private String name;
    private org.bson.Document docOfFields;
    private ObjectId createdByUserId;

    public SpecFile() {
    }

    public SpecFile(ObjectId id, String name, org.bson.Document docOfFields) {
        this.id = id;
        this.name = name;
        this.docOfFields = docOfFields;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public org.bson.Document getDocOfFields() {
        return docOfFields;
    }

    public void setDocOfFields(org.bson.Document docOfFields) {
        this.docOfFields = docOfFields;
    }

    public ObjectId getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(ObjectId createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecFile specFile)) return false;
        return Objects.equals(getId(), specFile.getId()) && Objects.equals(getName(), specFile.getName()) && Objects.equals(getDocOfFields(), specFile.getDocOfFields());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDocOfFields());
    }

    @Override
    public String toString() {
        return "SpecFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", docOfFields=" + docOfFields +
                '}';
    }
}
