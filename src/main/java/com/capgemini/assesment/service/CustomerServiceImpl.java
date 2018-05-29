package com.capgemini.assesment.service;

import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.repository.CustomerRepository;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    @Qualifier("customerServiceMapper")
    @Autowired
    MapperFacade mapperFacade;
    @Autowired
    CustomerRepository repository;
    @Autowired
    private Tracer tracer;

    @Override
    public List<GetCustomerOutput> getAllCustomer() {
        List<GetCustomerOutput> customerOutputs = new ArrayList<>();
        repository.findAll().forEach(customer -> customerOutputs.add(mapperFacade.map(customer, GetCustomerOutput.class)));
        return customerOutputs;
    }

    @Override
    public GetCustomerOutput getCustomer(long id) throws CustomerNotFound {
        log.debug("getCustomer method start", tracer.getCurrentSpan().getTraceId());
        Optional<Customer> customer = Optional.ofNullable(repository.findOne(id));
        GetCustomerOutput output = mapperFacade.map(customer.orElseThrow(() -> new CustomerNotFound()), GetCustomerOutput.class);
        log.debug("getCustomer method finish", tracer.getCurrentSpan().getTraceId());
        return output;
    }

    @Override
    public GetCustomerOutput getCustomerByNationalityId(String nationalityId) throws CustomerNotFound {
        log.debug("getCustomerByNationalityId method start", tracer.getCurrentSpan().getTraceId());
        Optional<Customer> customer = repository.findByNationalityId(nationalityId);
        GetCustomerOutput output = mapperFacade.map(customer.orElseThrow(() -> new CustomerNotFound()), GetCustomerOutput.class);
        log.debug("getCustomerByNationalityId method finish", tracer.getCurrentSpan().getTraceId());
        return output;
    }

    @Override
    public AddCustomerOutput addCustomer(AddCustomerInput addCustomerInput) throws CustomerAlreadyExist {
        log.debug("addCustomer method start", tracer.getCurrentSpan().getTraceId());
        Optional<Customer> customer = repository.findByNationalityId(addCustomerInput.getNationalityId());
        if (customer.isPresent()) {
            throw new CustomerAlreadyExist();
        }
        Customer newCust = repository.save(mapperFacade.map(addCustomerInput, Customer.class));
        AddCustomerOutput addCustomerOutput = mapperFacade.map(newCust, AddCustomerOutput.class);
        log.debug("addCustomer method start", tracer.getCurrentSpan().getTraceId());
        return addCustomerOutput;
    }
}
