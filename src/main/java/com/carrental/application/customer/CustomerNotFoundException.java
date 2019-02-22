package com.carrental.application.customer;

public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomerNotFoundException(long id) {
        super("Could not find customer " + id);
    }
}