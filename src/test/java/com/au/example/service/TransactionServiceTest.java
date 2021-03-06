package com.au.example.service;

import com.au.example.data.entity.Account;
import com.au.example.data.entity.Customer;
import com.au.example.data.entity.Transaction;
import com.au.example.data.repository.AccountRepository;
import com.au.example.data.repository.TransactionRepository;
import com.au.example.listener.ApplicationStartup;
import com.au.example.service.exception.AccountNotFound;
import com.au.example.service.exception.ErrorCode;
import com.au.example.service.exception.InsufficientBalance;
import com.au.example.service.model.input.transaction.TransactionInput;
import com.au.example.service.model.output.transaction.TransactionResultOutput;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {


    @Qualifier("transactionServiceMapper")
    @Autowired
    MapperFacade mapperFacade;
    @MockBean
    TransactionRepository transactionRepository;
    @MockBean
    AccountRepository accountRepository;
    @Autowired
    private TransactionService transactionService;
    @MockBean
    private ApplicationStartup applicationStartup;

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
        } catch (AccountNotFound accountNotFound) {
            Assert.assertEquals(accountNotFound.getErrorCode(), ErrorCode.ACCOUNT_NOT_FOUND);
        }

        try {
            TransactionInput transactionInput = TransactionInput.builder().accountId(1).amount(-20).build();
            TransactionResultOutput transactionResultOutput = transactionService.doTransaction(transactionInput);
        } catch (InsufficientBalance insufficientBalance) {
            Assert.assertEquals(insufficientBalance.getErrorCode(), ErrorCode.INSUFFICENT_BALANCE);
        }
        TransactionInput transactionInput = TransactionInput.builder().accountId(1).amount(-5).build();
        TransactionResultOutput transactionResultOutput = transactionService.doTransaction(transactionInput);
        Assert.assertEquals(transactionResultOutput.getId(), 2);

    }

}
