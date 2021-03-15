package com.filmreview.springbootproject.exception;

import java.util.ArrayList;
import java.util.List;

public class BadResourceException extends Exception{
    private List<String> errorMessages = new ArrayList<>();
    public BadResourceException() {

    }
    public BadResourceException(String s) {
        super(s);
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void addErrorMessage(String film_is_null_or_empty) {
        this.errorMessages.add(film_is_null_or_empty);
    }
}
