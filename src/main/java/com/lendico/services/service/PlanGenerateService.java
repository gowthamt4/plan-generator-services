package com.lendico.services.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.lendico.services.exception.InvalidLoanDetailsException;
import com.lendico.services.rest.model.LoanDetails;
import com.lendico.services.rest.model.MonthlyRepayment;

import static com.lendico.services.util.PlanGeneratorUtil.*;

@Service
public class PlanGenerateService {
  
  public List<MonthlyRepayment> generatePlanPerMonth(LoanDetails loanDetails) {
    List<MonthlyRepayment> finalList = new ArrayList<>();
    List<MonthlyRepayment> comparisionList = new ArrayList<>();
    
    double interestRate = (strToDoubleRound(loanDetails.getNominalRate())/12)/100.00;
    double initialStandingAmount = strToDoubleRound(loanDetails.getLoanAmount());
    double initialAnnuity = doubleRoundOffTwoDecimals((interestRate * initialStandingAmount)/(1-Math.pow((1+interestRate), -loanDetails.getDuration())));
    
    if(loanDetails.getDuration() > 0 && interestRate > 0 && initialStandingAmount > 0) {
      Date startDate = strToDate(loanDetails.getStartDate());
      
      finalList = IntStream.rangeClosed(1, loanDetails.getDuration())
          .mapToObj(month -> {
              double interest = 0.0;
              double principal = 0.0;
              double prevMonthRemainingPrincipal = 0.0;
              MonthlyRepayment monthlyRepayment = new MonthlyRepayment();
              monthlyRepayment.setBorrowerPaymentAmount(String.valueOf(initialAnnuity));
              
              if(comparisionList.isEmpty()) {
                interest = doubleRoundOffTwoDecimals(interestRate * initialStandingAmount);
                principal = doubleRoundOffTwoDecimals(initialAnnuity - interest);
                monthlyRepayment.setRemainingOutstandingPrincipal(String.valueOf(initialStandingAmount - principal));
                monthlyRepayment.setInitialOutstandingPrincipal(String.valueOf(initialStandingAmount));
                monthlyRepayment.setDate(dateToStr(startDate));
              } else {
                MonthlyRepayment prevMonth = comparisionList.get(month - 2);
                prevMonthRemainingPrincipal = strToDoubleRound(prevMonth.getRemainingOutstandingPrincipal());
                interest = doubleRoundOffTwoDecimals(interestRate * prevMonthRemainingPrincipal);
                principal = doubleRoundOffTwoDecimals(initialAnnuity - interest);
                if(principal > prevMonthRemainingPrincipal) {
                  principal = prevMonthRemainingPrincipal;
                  monthlyRepayment.setBorrowerPaymentAmount(String.valueOf(doubleRoundOffTwoDecimals(interest + principal)));
                }
                monthlyRepayment.setDate(dateToStr(addMonth(startDate, month-1)));
                monthlyRepayment.setInitialOutstandingPrincipal(String.valueOf(prevMonthRemainingPrincipal));
                monthlyRepayment.setRemainingOutstandingPrincipal(String.valueOf(doubleRoundOffTwoDecimals(prevMonthRemainingPrincipal - principal)));
              }
              
              monthlyRepayment.setPrincipal(String.valueOf(principal));
              monthlyRepayment.setInterest(String.valueOf(interest));
              comparisionList.add(monthlyRepayment);
              return monthlyRepayment;
          })
          .collect(Collectors.toList());
    } else {
      throw new InvalidLoanDetailsException("Make sure Interest Rate, Duration and Loan Amount are valid");
    }
    
    return finalList;
  }
}
