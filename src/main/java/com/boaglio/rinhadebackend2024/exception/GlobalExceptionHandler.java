package com.boaglio.rinhadebackend2024.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class.getSimpleName());

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalExceptions(Exception ex, WebRequest request) {

        var status = HttpStatus.INTERNAL_SERVER_ERROR;

        // erros 400 precisam voltar 422
        if (ex instanceof org.springframework.web.bind.MethodArgumentNotValidException ||
            ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else {
            log.error("Erro 500",ex);
        }

        return new ResponseEntity<>(ex.getMessage(), status);
    }
}