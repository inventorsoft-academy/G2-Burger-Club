package com.inventorsoft.Exception;

/**
 * Created by Sergey on 27.01.2018.
 */
public class UserAlreadyExistsException extends Exception {

    public UserAlreadyExistsException() {
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
