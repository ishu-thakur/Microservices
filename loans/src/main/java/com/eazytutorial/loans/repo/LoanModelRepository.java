package com.eazytutorial.loans.repo;

import com.eazytutorial.loans.model.Loans;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanModelRepository extends CrudRepository<Loans, String> {
    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);
}
