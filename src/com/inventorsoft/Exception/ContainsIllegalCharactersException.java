package com.inventorsoft.Exception;

/**
 * Created by Sergey on 27.01.2018.
 */
public class ContainsIllegalCharactersException extends Exception {
    public ContainsIllegalCharactersException() {
    }

    public ContainsIllegalCharactersException(String message) {
        super(message);
    }
}
