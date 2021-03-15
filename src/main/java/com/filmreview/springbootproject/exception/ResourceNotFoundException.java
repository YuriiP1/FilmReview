package com.filmreview.springbootproject.exception;

public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
