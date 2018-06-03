package com.au.example.service;

import com.au.example.service.model.input.transaction.TransactionInput;
import com.au.example.service.exception.AccountNotFound;
import com.au.example.service.exception.InsufficientBalance;
import com.au.example.service.model.output.transaction.TransactionResultOutput;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public interface TransactionService {
    TransactionResultOutput doTransaction(TransactionInput transactionInout) throws AccountNotFound, InsufficientBalance;
}
