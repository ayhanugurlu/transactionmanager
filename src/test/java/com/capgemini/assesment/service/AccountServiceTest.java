package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.data.repository.AccountRepository;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.listener.ApplicationStartup;
import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.exception.ErrorCode;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
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

import java.util.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Qualifier("accountServiceMapper")
    @Autowired
    MapperFacade mapperFacade;
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @MockBean
    private ApplicationStartup applicationStartup;

    @MockBean
    private Tracer tracer;

    @MockBean
    private Span span;

    @Before
    public void setUp() throws Exception {
        TransactionResultOutput transactionResultOutput = TransactionResultOutput.builder().id(1).build();
        when(span.getTraceId()).thenReturn(1l);
        when(tracer.getCurrentSpan()).thenReturn(span);
        Customer customer = Customer.builder().id(2).nationalityId("a").name("name").surname("surname").build();
        Account account = Account.builder().id(1).customer(customer).currencyType("TRY").balance(10).build();
        Transaction transaction = Transaction.builder().transactionDate(new Date()).account(account).amount(10).build();
        Set<Transaction> transactions = new HashSet<>();
        transactions.add(transaction);
        account.setTransactions(transactions);
        when(customerRepository.findOne(any(Long.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(accountRepository.findOne(1l)).thenReturn(null);
        when(accountRepository.findOne(2l)).thenReturn(account);
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        when(accountRepository.findAccountsByCustomer_Id(any(Long.class))).thenReturn(accounts);
        when(transactionService.doTransaction(any(TransactionInput.class))).thenReturn(transactionResultOutput);
    }

    @Test
    public void accountServiceTest() throws CustomerNotFound, AccountNotFound, InsufficientBalance {
        AddCustomerAccountInput addCustomerAccountInput = AddCustomerAccountInput.builder().amount(10).ownerId(1).currencyType("TRY").build();
        AddCustomerAccountOutput addCustomerAccountOutput = accountService.addAccount(addCustomerAccountInput);
        Assert.assertEquals(addCustomerAccountOutput.getId(), 1l);
        Assert.assertEquals(addCustomerAccountOutput.getCurrencyType(), "TRY");
        Assert.assertEquals(addCustomerAccountOutput.getOwnerId(), 2);
        addCustomerAccountInput = AddCustomerAccountInput.builder().amount(-10).ownerId(1).currencyType("TRY").build();
        try {
            addCustomerAccountOutput = accountService.addAccount(addCustomerAccountInput);
        } catch (InsufficientBalance insufficientBalance) {
            Assert.assertEquals(insufficientBalance.getErrorCode(), ErrorCode.INSUFFICENT_BALANCE);
        }

        List<GetAccountOutput> getAccountOutputs = accountService.getCustomerAccounts(1);
        Assert.assertEquals(getAccountOutputs.size(), 1);

        try {
            GetAccountTransactionOutput getAccountTransactionOutput = accountService.getAccountTransactions(1);
        } catch (AccountNotFound accountNotFound) {
            Assert.assertEquals(accountNotFound.getErrorCode(), ErrorCode.ACCOUNT_NOT_FOUND);
        }
        GetAccountTransactionOutput getAccountTransactionOutput = accountService.getAccountTransactions(2);
        Assert.assertEquals(getAccountTransactionOutput.getTransactionOutputs().size(), 1);

    }
}
