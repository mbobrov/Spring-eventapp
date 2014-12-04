package com.eventapp.prototype.controller;

import com.eventapp.prototype.service.exception.UserAlreadyExistsException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class UserExceptionHandler { //extends ResponseEntityExceptionHandler {

    //@Override
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        //List<String> errors = new ArrayList<>(ex.getAllErrors().size());
        List<FieldError> fieldErrors = ex.getFieldErrors();
        /*StringBuilder sb;

        for (FieldError fieldError : fieldErrors) {
            sb = new StringBuilder();
            sb.append("Field: ").append(fieldError.getField()).append(", ");
            sb.append("Value: ").append(fieldError.getRejectedValue()).append(", ");
            sb.append("Message: ").append(fieldError.getDefaultMessage());
            errors.add(sb.toString());
        }

        List<ObjectError> globalErrors = ex.getGlobalErrors();

        for (ObjectError objectError : globalErrors) {
            sb = new StringBuilder();
            sb.append("Object: ").append(objectError.getObjectName()).append(", ");
            sb.append("Code: ").append(objectError.getCode()).append(", ");
            sb.append("Message: ").append(objectError.getDefaultMessage());
            errors.add(sb.toString());
        }

        DefaultErrorMessage errorMessage = new DefaultErrorMessage("RQ00051", "RQ_BODY_VALIDATION_ERROR", errors);*/
        return new ResponseEntity(fieldErrors, headers, status);

    }

    /*@ExceptionHandler(value={MissingServletRequestParameterException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestPartException.class})*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBadRequest(MethodArgumentNotValidException ex) {
        List<FieldError> list = ex.getBindingResult().getFieldErrors();
        //list.add(new FieldError("User", "name", ex.getMessage()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(list, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userExists(UserAlreadyExistsException ex) {
        List<FieldError> list = new ArrayList<FieldError>();
        list.add(new FieldError("User", "name", ex.getRejectedValue(), false, null, null, ex.getMessage()));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(list, headers, HttpStatus.PRECONDITION_FAILED);
    }

}
