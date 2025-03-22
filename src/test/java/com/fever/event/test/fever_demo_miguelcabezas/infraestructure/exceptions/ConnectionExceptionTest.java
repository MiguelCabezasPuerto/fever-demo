package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionExceptionTest {

    @Test
    public void testConnectionExceptionMessage() {
        String errorMessage = "Connection failed";
        ConnectionException exception = new ConnectionException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}