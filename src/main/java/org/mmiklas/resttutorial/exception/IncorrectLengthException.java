package org.mmiklas.resttutorial.exception;

@SuppressWarnings("serial")
public class IncorrectLengthException extends Exception {
    public IncorrectLengthException(String message) {
        super(message);
    }
}
