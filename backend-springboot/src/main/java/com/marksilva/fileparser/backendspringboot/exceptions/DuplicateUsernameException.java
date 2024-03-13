package com.marksilva.fileparser.backendspringboot.exceptions;

public class DuplicateUsernameException extends Exception{
    public DuplicateUsernameException(String msg){
        super(msg);
    }
}
