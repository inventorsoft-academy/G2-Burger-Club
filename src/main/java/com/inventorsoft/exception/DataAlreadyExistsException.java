package com.inventorsoft.exception;

public class DataAlreadyExistsException extends Exception {

    public DataAlreadyExistsException() {
    }

    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
