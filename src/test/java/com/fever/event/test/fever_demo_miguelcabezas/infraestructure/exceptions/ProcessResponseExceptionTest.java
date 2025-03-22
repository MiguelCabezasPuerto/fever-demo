package com.fever.event.test.fever_demo_miguelcabezas.infraestructure.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessResponseExceptionTest {

    @Test
    public void testProcessResponseExceptionMessage() {
        String errorMessage = "Processing failed";
        ProcessResponseException exception = new ProcessResponseException(errorMessage);

        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }
}