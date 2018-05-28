package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.data.repository.AccountRepository;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.data.repository.TransactionRepository;
import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import com.capgemini.assesment.service.model.output.transaction.TransactionResultOutput;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {


    @Autowired
    private TransactionService transactionService;

    @Qualifier("transactionServiceMapper")
    @Autowired
    MapperFacade mapperFacade;

    @MockBean
    TransactionRepository transactionRepository;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    private Tracer tracer;

    @MockBean
    private Span span;


    @Before
    public void setUp() throws Exception {

        when(span.getTraceId()).thenReturn(1l);
        when(tracer.getCurrentSpan()).thenReturn(span);
        Customer customer = Customer.builder().id(2).nationalityId("a").name("name").surname("surname").build();
        Account account = Account.builder().id(1).customer(customer).currencyType("TRY").balance(10).build();
        Transaction transaction = Transaction.builder().transactionDate(new Date()).account(account).amount(10).id(2).build();
        when(accountRepository.findOne(2l)).thenReturn(null);
        when(accountRepository.findOne(1l)).thenReturn(account);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

    }


    @Test
    public void doTransactionTest() throws AccountNotFound, InsufficientBalance {


        try {
            TransactionInput transactionInput = TransactionInput.builder().accountId(2).amount(10).build();
            TransactionResultOutput transactionResultOutput = transactionService.doTransaction(transactionInput);
        }catch (AccountNotFound accountNotFound){

        }

        try {
            TransactionInput transactionInput = TransactionInput.builder().accountId(1).amount(-20).build();
            TransactionResultOutput transactionResultOutput = transactionService.doTransaction(transactionInput);
        }catch (InsufficientBalance insufficientBalance){

        }
        TransactionInput transactionInput = TransactionInput.builder().accountId(1).amount(-5).build();
        TransactionResultOutput transactionResultOutput = transactionService.doTransaction(transactionInput);
        Assert.assertEquals(transactionResultOutput.getId() , 2);

    }

}
