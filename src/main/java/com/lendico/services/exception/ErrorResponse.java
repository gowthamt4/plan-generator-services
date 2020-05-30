package com.lendico.services.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
  
  private String message;
  
  private int status;
  
  private String error;
  
  private List<String> details;
}
