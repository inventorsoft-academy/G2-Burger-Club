package com.inventorsoft.Exception;

/**
 * Created by Sergey on 27.01.2018.
 */
public class DataAlreadyExistsException extends Exception {

    public DataAlreadyExistsException() {
    }

    public DataAlreadyExistsException(String message) {
        super(message);
    }
}
