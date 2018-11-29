package org.priyanka.cmpe220.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidNewsException extends RuntimeException {

    public InvalidNewsException(String message) {
        super(message);
    }

}
