package com.carrental.application.car;

public class CarNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CarNotFoundException(long id) {
        super("Could not find car " + id);
    }
}