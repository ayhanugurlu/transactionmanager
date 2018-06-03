package com.au.example.data.repository;

import com.au.example.data.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findByNationalityId(String nationalityId);
}
