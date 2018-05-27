package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.repository.AccountRepository;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Service
public class AccountServiceImpl implements AccountService{

    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private Tracer tracer;


    @Qualifier("accountServiceMapper")
    @Autowired
    MapperFacade mapperFacade;

    @Autowired
    AccountRepository repository;




    @Override
    public AddCustomerAccountOutput addAccount(AddCustomerAccountInput addCustomerAccountInput) throws CustomerNotFound {
        logger.debug("addAccount method start", tracer.getCurrentSpan().getTraceId());
        Account account = mapperFacade.map(addCustomerAccountInput, Account.class);
        try {
            repository.save(account);
        }catch (ConstraintViolationException e){
            throw  new CustomerNotFound();
        }

        AddCustomerAccountOutput addCustomerAccountOutput = mapperFacade.map(account,AddCustomerAccountOutput.class);
        logger.debug("addAccount method finish", tracer.getCurrentSpan().getTraceId());
        return addCustomerAccountOutput;
    }
}
