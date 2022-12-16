package com.golfzonaca.backoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.file.convert")
public class FileUploadFailureException extends RuntimeException {
    public FileUploadFailureException(String message) {
        super(message);
    }
}
