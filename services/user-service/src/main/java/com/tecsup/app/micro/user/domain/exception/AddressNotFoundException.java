package com.tecsup.app.micro.user.domain.exception;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String msg) {
        super(msg);
    }
}