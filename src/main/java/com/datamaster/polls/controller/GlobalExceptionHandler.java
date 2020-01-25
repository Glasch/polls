package com.datamaster.polls.controller;

import com.datamaster.polls.dto.response.ErrorDTO;
import com.datamaster.polls.exception.ObjectNotFoundException;
import com.datamaster.polls.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ErrorDTO checkField(HttpServletRequest request, HttpServletResponse response, ObjectNotFoundException ex) {
        return new ErrorDTO(ex);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationFailedException.class)
    public ErrorDTO checkParams(HttpServletRequest request, HttpServletResponse response, ValidationFailedException ex) {
        return new ErrorDTO(ex);
    }
}
