package com.inventorsoft.Exception;

/**
 * Created by Sergey on 27.01.2018.
 */
public class WrongLoginPasswordException extends Exception {
    public WrongLoginPasswordException() {
    }

    public WrongLoginPasswordException(String message) {
        super(message);
    }
}
