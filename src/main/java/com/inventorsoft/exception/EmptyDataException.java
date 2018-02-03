package com.inventorsoft.exception;

public class EmptyDataException extends Exception{

    public EmptyDataException() {
    }

    public EmptyDataException(String message) {
        super(message);
    }
}
