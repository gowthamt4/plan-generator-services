package com.lendico.services.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.services.rest.model.LoanDetails;
import com.lendico.services.rest.model.MonthlyRepayment;
import com.lendico.services.service.PlanGenerateService;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PlanGenerateController {

  @Autowired
  private PlanGenerateService planGenerateService;
  
  @PostMapping("/generate-plan")
  public List<MonthlyRepayment> generatePlan(@Valid @RequestBody LoanDetails loanDetails){
    return planGenerateService.generatePlanPerMonth(loanDetails);
  }
}
