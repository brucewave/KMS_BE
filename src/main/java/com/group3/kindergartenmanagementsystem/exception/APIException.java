package com.group3.kindergartenmanagementsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class APIException extends RuntimeException{
    HttpStatus status;
    String message;
    public APIException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
