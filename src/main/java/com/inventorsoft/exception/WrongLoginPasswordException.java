package com.inventorsoft.exception;

public class WrongLoginPasswordException extends Exception {

    public WrongLoginPasswordException() {
    }

    public WrongLoginPasswordException(String message) {
        super(message);
    }
}
