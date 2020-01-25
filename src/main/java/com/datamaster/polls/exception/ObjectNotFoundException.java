package com.datamaster.polls.exception;

import java.util.UUID;

public class ObjectNotFoundException extends Exception {

    private static final long serialVersionUID = 5115812350906219106L;

    private static final String NOT_FOUND_MESSAGE = "%s with id '%s' is not found";

    public ObjectNotFoundException(Class clazz, UUID id) {
        super(String.format(NOT_FOUND_MESSAGE, clazz.getSimpleName(), id));
    }

}
