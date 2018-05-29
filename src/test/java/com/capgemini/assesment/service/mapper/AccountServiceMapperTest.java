package com.capgemini.assesment.service.mapper;

import com.capgemini.assesment.TransactionmanagerApplication;
import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TransactionmanagerApplication.class)
public class AccountServiceMapperTest {


    @Autowired
    AccountServiceMapper accountServiceMapper;

    @Test
    public void shouldMapAddCustomerAccountInputToAccount() {

        AddCustomerAccountInput addCustomerAccountInput = AddCustomerAccountInput.builder().ownerId(1).currencyType("TRY").build();
        Account account = accountServiceMapper.map(addCustomerAccountInput, Account.class);
        Assert.assertEquals(account.getCustomer().getId(), addCustomerAccountInput.getOwnerId());
        Assert.assertEquals(account.getCurrencyType(), addCustomerAccountInput.getCurrencyType());
    }


    @Test
    public void shouldMapAddCustomerAccountToAddCustomerAccountOutput() {

        Customer customer = Customer.builder().id(1).nationalityId("a").name("name").surname("surname").build();
        Set<Transaction> transactions = new HashSet<>();
        Account account = Account.builder().id(1).customer(customer).transactions(transactions).currencyType("TRY").balance(10).build();
        transactions.add(Transaction.builder().id(11).account(account).amount(101).transactionDate(new Date()).build());
        transactions.add(Transaction.builder().id(12).account(account).amount(101).transactionDate(new Date()).build());

        AddCustomerAccountOutput addCustomerAccountOutput = accountServiceMapper.map(account, AddCustomerAccountOutput.class);
        Assert.assertEquals(addCustomerAccountOutput.getOwnerId(), account.getCustomer().getId());
        Assert.assertEquals(addCustomerAccountOutput.getCurrencyType(), account.getCurrencyType());
        Assert.assertEquals(addCustomerAccountOutput.getId(), account.getId());
    }

    @Test
    public void shouldMapAccountToGetAccountTransactionOutput() {
        Customer customer = Customer.builder().id(1).nationalityId("a").name("name").surname("surname").build();
        Set<Transaction> transactions = new HashSet<>();
        Account account = Account.builder().id(1).customer(customer).transactions(transactions).currencyType("TRY").balance(10).build();
        transactions.add(Transaction.builder().id(11).account(account).amount(101).transactionDate(new Date()).build());
        transactions.add(Transaction.builder().id(12).account(account).amount(101).transactionDate(new Date()).build());
        GetAccountTransactionOutput getAccountTransactionOutput = accountServiceMapper.map(account, GetAccountTransactionOutput.class);
        Assert.assertEquals(getAccountTransactionOutput.getName(), account.getCustomer().getName());
        Assert.assertEquals(getAccountTransactionOutput.getSurname(), account.getCustomer().getSurname());
        Assert.assertEquals(getAccountTransactionOutput.getBalance(), account.getBalance());

    }

    @Test
    public void shouldMapTransactionToTransactionOutput() {
        Customer customer = Customer.builder().id(1).nationalityId("a").name("name").surname("surname").build();
        Set<Transaction> transactions = new HashSet<>();
        Account account = Account.builder().id(1).customer(customer).transactions(transactions).currencyType("TRY").balance(10).build();
        transactions.add(Transaction.builder().id(11).account(account).amount(101).transactionDate(new Date()).build());
        Transaction transaction = Transaction.builder().id(12).account(account).amount(101).transactionDate(new Date()).build();
        transactions.add(transaction);
        TransactionOutput transactionOutput = accountServiceMapper.map(transaction, TransactionOutput.class);
        Assert.assertEquals(transactionOutput.getAmount(), transaction.getAmount());
        Assert.assertEquals(transactionOutput.getTransactionDate(), transaction.getTransactionDate());
    }
}
