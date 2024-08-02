package com.group3.kindergartenmanagementsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@AllArgsConstructor
public class ForbiddenAccessException extends RuntimeException{
    HttpStatus httpStatus;
    String message;

    public String getMessage() {
        return message;
    }
}
