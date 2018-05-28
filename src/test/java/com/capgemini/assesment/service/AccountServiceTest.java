package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.repository.AccountRepository;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;


/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Qualifier("accountServiceMapper")
    @Autowired
    MapperFacade mapperFacade;

    @MockBean
    AccountRepository accountRepository;
    @MockBean
    CustomerRepository customerRepository;


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
        when(customerRepository.findOne(any(Long.class))).thenReturn(customer);
        when(accountRepository.save(any(Account.class))).thenReturn(account);
    }

    @Test
    public void addAccountTest() throws CustomerNotFound {
        AddCustomerAccountInput addCustomerAccountInput = AddCustomerAccountInput.builder().ownerId(1).currencyType("TRY").build();
        AddCustomerAccountOutput addCustomerAccountOutput = accountService.addAccount(addCustomerAccountInput);
        Assert.assertEquals(addCustomerAccountOutput.getId(), 1l);
        Assert.assertEquals(addCustomerAccountOutput.getCurrencyType(), "TRY");
        Assert.assertEquals(addCustomerAccountOutput.getOwnerId(), 2l);
    }
}
