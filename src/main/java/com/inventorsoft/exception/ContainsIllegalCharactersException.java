package com.inventorsoft.exception;

public class ContainsIllegalCharactersException extends Exception {
    public ContainsIllegalCharactersException() {
    }

    public ContainsIllegalCharactersException(String message) {
        super(message);
    }
}
