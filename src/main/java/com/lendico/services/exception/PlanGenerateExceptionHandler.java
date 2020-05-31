package com.lendico.services.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class PlanGenerateExceptionHandler {
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentInavlidException(MethodArgumentNotValidException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    for(ObjectError error : ex.getBindingResult().getAllErrors()) {
        details.add(error.getDefaultMessage());
    }
    ErrorResponse error = new ErrorResponse("Invalid Request Body", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), details);
    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(InvalidLoanDetailsException.class)
  public ResponseEntity<Object> handleInvalidLoanDetailsException(InvalidLoanDetailsException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());
    ErrorResponse error = new ErrorResponse("Invalid Request Body", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), details);
    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(InvalidDoubleFormatException.class)
  public ResponseEntity<Object> handleInValidDoubleFormatException(InvalidDoubleFormatException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());
    ErrorResponse error = new ErrorResponse("Invalid Request Body", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), details);
    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(InvalidDateFormatException.class)
  public ResponseEntity<Object> handleInvalidDateFormatException(InvalidDateFormatException ex, WebRequest request) {
    List<String> details = new ArrayList<>();
    details.add(ex.getMessage());
    ErrorResponse error = new ErrorResponse("Invalid Request Body", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), details);
    return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
  }
}
