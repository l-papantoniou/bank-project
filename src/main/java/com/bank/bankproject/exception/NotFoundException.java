package com.bank.bankproject.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1595761711781741976L;

    public NotFoundException(String message) {
        super(message);
    }

}
