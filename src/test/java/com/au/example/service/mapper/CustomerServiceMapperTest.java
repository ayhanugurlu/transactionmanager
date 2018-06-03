package com.au.example.service.mapper;

import com.au.example.data.entity.Customer;
import com.au.example.service.model.input.customer.AddCustomerInput;
import com.au.example.service.model.output.customer.AddCustomerOutput;
import com.au.example.service.model.output.customer.GetCustomerOutput;
import com.au.example.TransactionManagerApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TransactionManagerApplication.class)
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
