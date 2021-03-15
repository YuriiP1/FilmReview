package com.filmreview.springbootproject.exception;

public class ResourceAlreadyExistsException extends Exception {

    public ResourceAlreadyExistsException() {

    }
    public ResourceAlreadyExistsException(String s) {
        super(s);
    }
}
