package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.exceptions;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }
}
