package org.priyanka.cmpe220.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyRegisteredUserException extends RuntimeException {

    public AlreadyRegisteredUserException() {

    }

    public AlreadyRegisteredUserException(String message) {
        super(message);
    }

}
