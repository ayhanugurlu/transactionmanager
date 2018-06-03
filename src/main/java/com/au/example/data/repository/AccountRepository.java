package com.au.example.data.repository;

import com.au.example.data.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAccountsByCustomer_Id(long customerId);
}
