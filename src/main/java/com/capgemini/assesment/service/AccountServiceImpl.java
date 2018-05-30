package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.repository.AccountRepository;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {


    @Qualifier("accountServiceMapper")
    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TransactionService transactionService;
    @Autowired
    private Tracer tracer;

    @Transactional
    @Override
    public AddCustomerAccountOutput addAccount(AddCustomerAccountInput addCustomerAccountInput) throws CustomerNotFound, AccountNotFound, InsufficientBalance {
        log.debug("addAccount method start", tracer.getCurrentSpan().getTraceId());
        Optional.ofNullable(customerRepository.findOne(addCustomerAccountInput.getOwnerId())).orElseThrow(() -> new CustomerNotFound());
        Account account = mapperFacade.map(addCustomerAccountInput, Account.class);
        if (addCustomerAccountInput.getAmount() < 0) {
            throw new InsufficientBalance();
        }
        account = accountRepository.save(account);
        if (addCustomerAccountInput.getAmount() > 0) {
            TransactionInput transactionInput = TransactionInput.builder().amount(addCustomerAccountInput.getAmount()).accountId(account.getId()).build();
            transactionService.doTransaction(transactionInput);

        }
        AddCustomerAccountOutput addCustomerAccountOutput = mapperFacade.map(account, AddCustomerAccountOutput.class);
        log.debug("addAccount method finish", tracer.getCurrentSpan().getTraceId());
        return addCustomerAccountOutput;
    }


    @Transactional
    @Override
    public GetAccountTransactionOutput getAccountTransactions(long accountId) throws AccountNotFound {
        log.debug("getAccountTransactions method start", tracer.getCurrentSpan().getTraceId());
        Account account = accountRepository.findOne(accountId);
        if (account == null) {
            throw new AccountNotFound();
        }
        GetAccountTransactionOutput getAccountTransactionOutput = mapperFacade.map(account, GetAccountTransactionOutput.class);
        getAccountTransactionOutput.setTransactionOutputs(account.getTransactions().stream().map(transaction -> mapperFacade.map(transaction, TransactionOutput.class)).collect(Collectors.toList()));
        log.debug("getAccountTransactions method finish", tracer.getCurrentSpan().getTraceId());
        return getAccountTransactionOutput;
    }

    @Override
    public List<GetAccountOutput> getCustomerAccounts(long customerId) {
        List<Account> accounts = accountRepository.findAccountsByCustomer_Id(customerId);
        List<GetAccountOutput> result = accounts.stream().map(account -> mapperFacade.map(account, GetAccountOutput.class)).collect(Collectors.toList());
        return result;
    }

}
