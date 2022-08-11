package com.z.useraccountmgmt.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class ValidationException extends RuntimeException {
    private  static final long serialVersionUID = 1L;
    public ValidationException(String message){super(message);}
}
