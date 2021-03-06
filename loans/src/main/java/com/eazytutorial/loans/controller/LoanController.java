package com.eazytutorial.loans.controller;

import com.eazytutorial.loans.config.LoansServiceConfig;
import com.eazytutorial.loans.model.Customer;
import com.eazytutorial.loans.model.Loans;
import com.eazytutorial.loans.model.Properties;
import com.eazytutorial.loans.repo.LoanModelRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoanController {
    public static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanModelRepository loansRepository;
    @Autowired
    private LoansServiceConfig loansServiceConfig;

    @PostMapping("/myLoans")
    public List<Loans> getLoansDetails(@RequestBody Customer customer) {
        logger.info("getLoansDetails() has started");
        List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
        logger.info("getLoansDetails() has ended");
        if (loans != null) {
            return loans;
        } else {
            return null;
        }

    }

    @GetMapping("/loans/properties")
    public String getServerDetails() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(loansServiceConfig.getMsg(), loansServiceConfig.getBuildVersion(), loansServiceConfig.getMailDetails(), loansServiceConfig.getActiveBranches());
        return objectWriter.writeValueAsString(properties);
    }
}
