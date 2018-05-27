package com.capgemini.assesment.service;

import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.transaction.TransactionOutput;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public interface TransactionService {
    TransactionOutput doTransaction(TransactionInput transactionInout) throws AccountNotFound, InsufficientBalance;
}
