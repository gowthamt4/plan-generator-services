package com.lendico.services.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyRepayment {

  private String borrowerPaymentAmount;
  
  //private String date;
  
  private String initialOutstandingPrincipal;
  
  private String interest;
  
  private String principal;
  
  private String remainingOutstandingPrincipal;
}
