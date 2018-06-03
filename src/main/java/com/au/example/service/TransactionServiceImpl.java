package com.au.example.service;

import com.au.example.data.entity.Account;
import com.au.example.data.entity.Transaction;
import com.au.example.data.repository.AccountRepository;
import com.au.example.data.repository.TransactionRepository;
import com.au.example.service.model.input.transaction.TransactionInput;
import com.au.example.service.exception.AccountNotFound;
import com.au.example.service.exception.InsufficientBalance;
import com.au.example.service.model.output.transaction.TransactionResultOutput;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Slf4j
@Component
public class TransactionServiceImpl implements TransactionService {


    @Qualifier("accountServiceMapper")
    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    private Tracer tracer;

    @Transactional
    @Override
    public TransactionResultOutput doTransaction(TransactionInput transactionInput) throws AccountNotFound, InsufficientBalance {
        Optional<Account> account = Optional.ofNullable(accountRepository.findOne(transactionInput.getAccountId()));
        account.orElseThrow(() -> new AccountNotFound(transactionInput.getAccountId()));
        long total = account.get().getBalance() + transactionInput.getAmount();
        if (total < 0) {
            throw new InsufficientBalance();
        }
        Transaction transaction = mapperFacade.map(transactionInput, Transaction.class);
        account.get().setBalance(total);
        transaction.setAccount(account.get());
        transaction.setTransactionDate(new Date());
        transaction = transactionRepository.save(transaction);
        TransactionResultOutput transactionResultOutput = mapperFacade.map(transaction, TransactionResultOutput.class);
        return transactionResultOutput;
    }
}
