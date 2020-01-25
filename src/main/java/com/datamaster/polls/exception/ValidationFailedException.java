package com.datamaster.polls.exception;

public class ValidationFailedException extends Exception {
    private static final long serialVersionUID = 3616757354848505659L;

    private static final String VALIDATION_FAILED_MESSAGE = "Please check required parameter(s): %s";

    public ValidationFailedException(String parameters) {
        super(String.format(VALIDATION_FAILED_MESSAGE, parameters));
    }
}
