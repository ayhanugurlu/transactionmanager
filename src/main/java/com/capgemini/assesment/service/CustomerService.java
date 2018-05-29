package com.capgemini.assesment.service;

import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;

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
