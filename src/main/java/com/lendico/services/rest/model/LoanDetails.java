package com.lendico.services.rest.model;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDetails implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -4071369944828498482L;
  
  @NotNull(message = "Loan Amount is missing in the request body")
  private String loanAmount;
  
  @NotNull(message = "Interest Rate or Nominal Rate is missing in the request body")
  private String nominalRate;
  
  @NotNull(message = "Duration of the loan is missing in the request body")
  @Positive(message = "Duration of the loan should be positive")
  private int duration;
  
  @NotNull(message = "StartDate of the loan is missing")
  private String startDate;

}
