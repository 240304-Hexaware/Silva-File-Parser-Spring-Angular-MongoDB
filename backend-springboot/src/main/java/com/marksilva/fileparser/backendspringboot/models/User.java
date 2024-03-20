package com.marksilva.fileparser.backendspringboot.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("users")
public class User{
    @Id
    private ObjectId id;
    //TODO: Make UserName Unique
    private String username;
    private String password;
    private List<ObjectId> listOfSpecFileIds;
    private List<ObjectId> listOfParsedFileIds;

    public User() {
        super();
        this.listOfParsedFileIds = new ArrayList<>();
        this.listOfSpecFileIds = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.listOfParsedFileIds = new ArrayList<>();
        this.listOfSpecFileIds = new ArrayList<>();
    }

    public User(ObjectId id, String username, String password, List<ObjectId> listOfSpecFileIds, List<ObjectId> listOfParsedFileIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.listOfSpecFileIds = listOfSpecFileIds;
        this.listOfParsedFileIds = listOfParsedFileIds;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ObjectId> getListOfSpecFileIds() {
        return listOfSpecFileIds;
    }

    public void setListOfSpecFileIds(List<ObjectId> listOfSpecFileIds) {
        this.listOfSpecFileIds = listOfSpecFileIds;
    }

    public List<ObjectId> getListOfParsedFileIds() {
        return listOfParsedFileIds;
    }

    public void setListOfParsedFileIds(List<ObjectId> listOfParsedFileIds) {
        this.listOfParsedFileIds = listOfParsedFileIds;
    }
}
