package com.inventorsoft.Exception;

/**
 * Created by Sergey on 27.01.2018.
 */
public class EmptyDataException extends Exception{
    public EmptyDataException() {
    }

    public EmptyDataException(String message) {
        super(message);
    }
}
