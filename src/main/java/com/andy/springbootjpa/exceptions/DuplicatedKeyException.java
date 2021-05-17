package com.andy.springbootjpa.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicatedKeyException extends Exception{
    public DuplicatedKeyException(String message) {
        super(message);
    }
}



