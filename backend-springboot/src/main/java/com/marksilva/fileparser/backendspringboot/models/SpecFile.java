package com.marksilva.fileparser.backendspringboot.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Document("specFiles")
public class SpecFile {
    @Id
    private ObjectId id;
    private String name;
    private org.bson.Document[] listOfFields;

    public SpecFile() {
    }

    public SpecFile(ObjectId id, String name, org.bson.Document[] listOfFields) {
        this.id = id;
        this.name = name;
        this.listOfFields = listOfFields;
    }

    public Map<String, Field> parseSpec(File specFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(specFile, new TypeReference<Map<String, Field>>() {});
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
