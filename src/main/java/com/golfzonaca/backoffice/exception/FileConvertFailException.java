package com.golfzonaca.backoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.file.convert")
public class FileConvertFailException extends NoSuchElementException {
    public FileConvertFailException(String s) {
        super(s);
    }
}
