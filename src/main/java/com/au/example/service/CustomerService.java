package com.au.example.service;

import com.au.example.service.model.output.customer.AddCustomerOutput;
import com.au.example.service.model.output.customer.GetCustomerOutput;
import com.au.example.service.exception.CustomerAlreadyExist;
import com.au.example.service.exception.CustomerNotFound;
import com.au.example.service.model.input.customer.AddCustomerInput;

import java.util.List;


/**
 * Created by ayhanugurlu on 5/26/18.
 */
public interface CustomerService {

    List<GetCustomerOutput> getAllCustomer();

    GetCustomerOutput getCustomer(long id) throws CustomerNotFound;

    GetCustomerOutput getCustomerByNationalityId(String nationalityId) throws CustomerNotFound;

    AddCustomerOutput addCustomer(AddCustomerInput addCustomerInput) throws CustomerAlreadyExist;
}
