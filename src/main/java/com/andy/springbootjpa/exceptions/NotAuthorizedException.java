package com.andy.springbootjpa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAuthorizedException extends Exception{
    public NotAuthorizedException(String message){
        super(message);
    }
}
