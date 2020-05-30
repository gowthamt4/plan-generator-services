package com.lendico.services.exception;

public class InvalidLoanDetailsException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 8497427525286367423L;

  public InvalidLoanDetailsException(String message) {
    super(message);
  }
}
