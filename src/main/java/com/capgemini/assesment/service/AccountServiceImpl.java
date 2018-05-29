package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.data.repository.AccountRepository;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import ma.glasnost.orika.MapperFacade;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private Tracer tracer;


    @Qualifier("accountServiceMapper")
    @Autowired
    MapperFacade mapperFacade;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;


    @Override
    public AddCustomerAccountOutput addAccount(AddCustomerAccountInput addCustomerAccountInput) throws CustomerNotFound {
        logger.debug("addAccount method start", tracer.getCurrentSpan().getTraceId());
        Optional.ofNullable(customerRepository.findOne(addCustomerAccountInput.getOwnerId())).orElseThrow(() -> new CustomerNotFound());
        Account account = mapperFacade.map(addCustomerAccountInput, Account.class);
        account = accountRepository.save(account);
        AddCustomerAccountOutput addCustomerAccountOutput = mapperFacade.map(account, AddCustomerAccountOutput.class);
        logger.debug("addAccount method finish", tracer.getCurrentSpan().getTraceId());
        return addCustomerAccountOutput;
    }


    @Override
    public GetAccountTransactionOutput getAccountTransactions(long accountId) throws AccountNotFound {
        logger.debug("getAccountTransactions method start", tracer.getCurrentSpan().getTraceId());
        Optional<Account> account = Optional.ofNullable(accountRepository.findOne(accountId));
        account.orElseThrow(() -> new AccountNotFound());
        GetAccountTransactionOutput getAccountTransactionOutput = mapperFacade.map(account.get(), GetAccountTransactionOutput.class);
        getAccountTransactionOutput.setTransactionOutputs(account.get().getTransactions().stream().map(transaction -> mapperFacade.map(transaction, TransactionOutput.class)).collect(Collectors.toList()));
        logger.debug("getAccountTransactions method finish", tracer.getCurrentSpan().getTraceId());
        return getAccountTransactionOutput;
    }

}
