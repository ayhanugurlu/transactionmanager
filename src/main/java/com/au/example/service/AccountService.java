package com.au.example.service;

import com.au.example.service.exception.AccountNotFound;
import com.au.example.service.exception.CustomerNotFound;
import com.au.example.service.exception.InsufficientBalance;
import com.au.example.service.model.input.account.AddCustomerAccountInput;
import com.au.example.service.model.output.account.AddCustomerAccountOutput;
import com.au.example.service.model.output.account.GetAccountOutput;
import com.au.example.service.model.output.account.GetAccountTransactionOutput;

import java.util.List;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public interface AccountService {
    AddCustomerAccountOutput addAccount(AddCustomerAccountInput addCustomerAccountInput) throws CustomerNotFound, AccountNotFound, InsufficientBalance;

    GetAccountTransactionOutput getAccountTransactions(long accountId) throws AccountNotFound;

    List<GetAccountOutput> getCustomerAccounts(long customerId);
}
