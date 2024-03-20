package com.marksilva.fileparser.backendspringboot.exceptions;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException(String msg){
        super(msg);
    }
}
