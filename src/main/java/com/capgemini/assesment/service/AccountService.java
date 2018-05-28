package com.capgemini.assesment.service;

import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
public interface AccountService {
    AddCustomerAccountOutput addAccount(AddCustomerAccountInput addCustomerAccountInput) throws CustomerNotFound;
    GetAccountTransactionOutput getAccountTransactions(long accountId) throws AccountNotFound;
}
