package com.lendico.services.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.lendico.services.rest.model.LoanDetails;
import com.lendico.services.rest.model.MonthlyRepayment;

@Service
public class PlanGenerateService {

  public List<MonthlyRepayment> generatePlanPerMonth(LoanDetails loanDetails) {
    List<MonthlyRepayment> finalList = new ArrayList<>();
    List<MonthlyRepayment> comparisionList = new ArrayList<>();
    
    double interestRate = (strToDoubleRound(loanDetails.getNominalRate())/12)/100.00;
    double initialStandingAmount = strToDoubleRound(loanDetails.getLoanAmount());
    double initialAnnuity = Math.round((interestRate * initialStandingAmount)/(1-Math.pow((1+interestRate), -loanDetails.getDuration()))*100)/100.00;
    
    finalList = IntStream.rangeClosed(1, loanDetails.getDuration())
        .mapToObj(month -> {
            double interest = 0.0;
            double principal = 0.0;
            double prevMonthRemainingPrincipal = 0.0;
            MonthlyRepayment monthlyRepayment = new MonthlyRepayment();
            if(comparisionList.isEmpty()) {
              interest = doubleRoundOffTwoDecimals(interestRate * initialStandingAmount);
              principal = doubleRoundOffTwoDecimals(initialAnnuity - interest);
              monthlyRepayment.setPrincipal(String.valueOf(principal));
              monthlyRepayment.setRemainingOutstandingPrincipal(String.valueOf(initialStandingAmount - principal));
              monthlyRepayment.setInitialOutstandingPrincipal(String.valueOf(initialStandingAmount));
            } else {
              MonthlyRepayment prevMonth = comparisionList.get(month - 2);
              prevMonthRemainingPrincipal = strToDoubleRound(prevMonth.getRemainingOutstandingPrincipal());
              interest = doubleRoundOffTwoDecimals(interestRate * prevMonthRemainingPrincipal);
              principal = doubleRoundOffTwoDecimals(initialAnnuity - interest);
              monthlyRepayment.setInitialOutstandingPrincipal(String.valueOf(prevMonthRemainingPrincipal));
              monthlyRepayment.setPrincipal(String.valueOf(principal));
              monthlyRepayment.setRemainingOutstandingPrincipal(String.valueOf(prevMonthRemainingPrincipal - principal));
            }
            monthlyRepayment.setInterest(String.valueOf(interest));
            monthlyRepayment.setBorrowerPaymentAmount(String.valueOf(initialAnnuity));
            comparisionList.add(monthlyRepayment);
            return monthlyRepayment;
        })
        .collect(Collectors.toList());
    
    return finalList;
  }
  
  private double strToDoubleRound(String str) {
    return Math.round(Double.valueOf(str)*100)/100.00; 
  }
  
  private double doubleRoundOffTwoDecimals(double doubleValue) {
    return Math.round(doubleValue*100)/100.00; 
  }

}
