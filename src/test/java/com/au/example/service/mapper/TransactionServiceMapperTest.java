package com.au.example.service.mapper;

import com.au.example.data.entity.Account;
import com.au.example.service.model.input.transaction.TransactionInput;
import com.au.example.TransactionManagerApplication;
import com.au.example.data.entity.Transaction;
import com.au.example.service.model.output.account.TransactionOutput;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TransactionManagerApplication.class)
public class TransactionServiceMapperTest {


    @Autowired
    TransactionServiceMapper transactionServiceMapper;

    @Test
    public void shouldMapTransactionInputToTransaction() {
        TransactionInput transactionInput = TransactionInput.builder().accountId(1).amount(10).build();
        Transaction transaction = transactionServiceMapper.map(transactionInput, Transaction.class);
        Assert.assertEquals(transaction.getAmount(), transactionInput.getAmount());
        Assert.assertEquals(transaction.getAccount().getId(), transactionInput.getAccountId());
    }

    @Test
    public void shouldMapTransactionToTransactionResultOutput() {
        Transaction transaction = Transaction.builder().account(Account.builder().id(1).build()).transactionDate(new Date()).build();
        TransactionOutput transactionOutput = transactionServiceMapper.map(transaction, TransactionOutput.class);
        Assert.assertEquals(transaction.getAmount(), transactionOutput.getAmount());
        Assert.assertEquals(transaction.getTransactionDate(), transactionOutput.getTransactionDate());
    }

}
