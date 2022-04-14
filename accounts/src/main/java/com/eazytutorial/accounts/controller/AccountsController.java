package com.eazytutorial.accounts.controller;

import com.eazytutorial.accounts.config.AccountsServiceConfig;
import com.eazytutorial.accounts.model.*;
import com.eazytutorial.accounts.repo.AccountsModelRepository;
import com.eazytutorial.accounts.service.client.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AccountsController {

    @Autowired
    private AccountsModelRepository repo;

    @Autowired
    private AccountsServiceConfig accountsServiceConfig;

    @Autowired
    private LoansFeignClient loansFeignClient;

    @PostMapping(value = "/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {

        Accounts accounts = repo.findByCustomerId(customer.getCustomerId());
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsServiceConfig.getMsg(), accountsServiceConfig.getBuildVersion(), accountsServiceConfig.getMailDetails(), accountsServiceConfig.getActiveBranches());
        String s = objectWriter.writeValueAsString(properties);
        return s;
    }

    @PostMapping("/CustomerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustomerDetailsFallBack")
    /*
     * @Retry(name = "retryForCustomerDetails", fallbackMethod = "myCustomerDetailsFallBack")
     * */
    public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {
        Accounts customerAccount = repo.findByCustomerId(customer.getCustomerId());
        List<Loans> loansDetails = loansFeignClient.getLoansDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(customerAccount);
        customerDetails.setLoans(loansDetails);

        return customerDetails;
    }


    private CustomerDetails myCustomerDetailsFallBack(Customer customer, Throwable throwable) {
        Accounts customerAccount = repo.findByCustomerId(customer.getCustomerId());
//            beacuse i am failling the loans so dont need to create loans as i want to return only
//            accounts details in my fall back method
//            List<Loans> loansDetails = loansFeignClient.getLoansDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(customerAccount);
//            customerDetails.setLoans(loansDetails);
        return customerDetails;
    }


    @GetMapping("/sayHello")
    @RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
    public String sayHello() {
        return "Hello, Welcome to eazyBank";
    }

    private String sayHelloFallback(Throwable throwable) {
        return "Hi, Welcome to eazyBank";
    }
}
