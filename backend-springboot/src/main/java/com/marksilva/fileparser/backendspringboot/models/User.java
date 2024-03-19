package com.marksilva.fileparser.backendspringboot.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document("users")
public class User implements UserDetails {
    @Id
    private ObjectId id;
    //TODO: Make UserName Unique
    private String username;
    private String password;
    private List<ObjectId> listOfSpecFileIds;
    private List<ObjectId> listOfParsedFileIds;

    private Set<Role> authorities;

    public User() {
        super();
        this.listOfParsedFileIds = new ArrayList<>();
        this.listOfSpecFileIds = new ArrayList<>();
        this.authorities = new HashSet<>();
    }

    public User(String username, String password, Set<Role> authorities) {
        this.username = username;
        this.password = password;
        this.listOfParsedFileIds = new ArrayList<>();
        this.listOfSpecFileIds = new ArrayList<>();
        this.authorities = authorities;
    }

    public User(ObjectId id, String username, String password, List<ObjectId> listOfSpecFileIds, List<ObjectId> listOfParsedFileIds, Set<Role> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.listOfSpecFileIds = listOfSpecFileIds;
        this.listOfParsedFileIds = listOfParsedFileIds;
        this.authorities = authorities;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Set<Role> authorities){
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
