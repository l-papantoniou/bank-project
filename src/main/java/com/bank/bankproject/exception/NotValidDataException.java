package com.bank.bankproject.exception;

public class NotValidDataException extends RuntimeException {
    private static final long serialVersionUID = 1595761711781741976L;

    public NotValidDataException(String message) {
        super(message);
    }

}
