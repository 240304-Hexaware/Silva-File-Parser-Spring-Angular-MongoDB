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
    private org.bson.Document[] listOfFields;

    public SpecFile() {
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

    public org.bson.Document[] getListOfFields() {
        return listOfFields;
    }

    public void setListOfFields(org.bson.Document[] listOfFields) {
        this.listOfFields = listOfFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecFile specFile)) return false;
        return Objects.equals(getId(), specFile.getId()) && Objects.equals(getName(), specFile.getName()) && Arrays.equals(getListOfFields(), specFile.getListOfFields());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName());
        result = 31 * result + Arrays.hashCode(getListOfFields());
        return result;
    }

    @Override
    public String toString() {
        return "SpecFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", listOfFields=" + Arrays.toString(listOfFields) +
                '}';
    }
}
