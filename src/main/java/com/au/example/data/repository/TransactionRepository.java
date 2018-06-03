package com.au.example.data.repository;

import com.au.example.data.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
