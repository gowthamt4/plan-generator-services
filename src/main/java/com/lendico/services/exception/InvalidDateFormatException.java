package com.lendico.services.exception;

public class InvalidDateFormatException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -6292217728093574798L;
  
  public InvalidDateFormatException(String message) {
    super(message);
  }
}
