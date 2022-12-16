package com.golfzonaca.backoffice.exhandler.advice;

import com.golfzonaca.backoffice.exception.FileConvertFailException;
import com.golfzonaca.backoffice.exception.FileUploadFailureException;
import com.golfzonaca.backoffice.exception.WrongAddressException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongAddressException.class)
    public String illegalExHandler(WrongAddressException e, Model model) {
        log.error("[exceptionHandle] ex", e);
        model.addAttribute("message", e.getMessage());
        return "error/4xx";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String illegalExHandler(NoSuchElementException e, Model model) {
        log.error("[exceptionHandle] ex", e);
        model.addAttribute("message", e.getMessage());
        return "error/4xx";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AuthenticationServiceException.class)
    public String illegalExHandler(AuthenticationServiceException e, Model model) {
        log.error("[exceptionHandle] ex", e);
        model.addAttribute("message", e.getMessage());
        return "error/4xx";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public String illegalExHandler(BadCredentialsException e, Model model) {
        log.error("[exceptionHandle] ex", e);
        model.addAttribute("message", e.getMessage());
        return "error/4xx";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileUploadFailureException.class)
    public String illegalExHandler(FileUploadFailureException e, Model model) {
        log.error("[exceptionHandle] ex", e);
        model.addAttribute("message", e.getMessage());
        return "error/5xx";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileConvertFailException.class)
    public String illegalExHandler(FileConvertFailException e, Model model) {
        log.error("[exceptionHandle] ex", e);
        model.addAttribute("message", e.getMessage());
        return "error/5xx";
    }
}
