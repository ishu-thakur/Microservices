package com.eazytutorial.accounts.repo;

import com.eazytutorial.accounts.model.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsModelRepository extends JpaRepository<Accounts, Integer> {
    Accounts findByCustomerId(int customerId);

}