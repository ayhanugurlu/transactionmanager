package com.capgemini.assesment.service;

import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.transaction.TransactionResultOutput;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public interface TransactionService {
    TransactionResultOutput doTransaction(TransactionInput transactionInout) throws AccountNotFound, InsufficientBalance;
}
