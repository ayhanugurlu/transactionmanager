package com.capgemini.assesment.data.repository;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public interface AccountRepository extends CrudRepository<Account,Long>{

    List<Account> findAccountsByCustomer_Id(long customerId);
}
