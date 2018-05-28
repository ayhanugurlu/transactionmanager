package com.capgemini.assesment.service.mapper;

import com.capgemini.assesment.TransactionmanagerApplication;
import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
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

import java.util.Date;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TransactionmanagerApplication.class)
public class CustomerServiceMapperTest {


    @Autowired
    CustomerServiceMapper customerServiceMapper;

    @Test
    public void shouldMapAddCustomerInputToCustomer() {
        AddCustomerInput addCustomerInput = AddCustomerInput.builder().nationalityId("1").name("name").surname("surname").build();
        Customer customer = customerServiceMapper.map(addCustomerInput, Customer.class);
        Assert.assertEquals(customer.getName(), addCustomerInput.getName());
        Assert.assertEquals(customer.getNationalityId(), addCustomerInput.getNationalityId());
        Assert.assertEquals(customer.getSurname(), addCustomerInput.getSurname());
    }



    @Test
    public void shouldMapCustomerToGetCustomerOutput() {
        Customer customer = Customer.builder().id(1).nationalityId("a").name("name").surname("surname").build();
        GetCustomerOutput getCustomerOutput = customerServiceMapper.map(customer, GetCustomerOutput.class);
        Assert.assertEquals(customer.getName(), getCustomerOutput.getName());
        Assert.assertEquals(customer.getNationalityId(), getCustomerOutput.getNationalityId());
        Assert.assertEquals(customer.getSurname(), getCustomerOutput.getSurname());
    }

    @Test
    public void shouldMapCustomerToAddCustomerOutput() {
        Customer customer = Customer.builder().id(1).nationalityId("a").name("name").surname("surname").build();
        AddCustomerOutput addCustomerOutput = customerServiceMapper.map(customer, AddCustomerOutput.class);
        Assert.assertEquals(customer.getName(), addCustomerOutput.getName());
        Assert.assertEquals(customer.getNationalityId(), addCustomerOutput.getNationalityId());
        Assert.assertEquals(customer.getSurname(), addCustomerOutput.getSurname());
    }

}
