package com.au.example.service.mapper;

import com.au.example.data.entity.Customer;
import com.au.example.service.model.output.customer.AddCustomerOutput;
import com.au.example.service.model.output.customer.GetCustomerOutput;
import com.au.example.service.model.input.customer.AddCustomerInput;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class CustomerServiceMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {

        factory.classMap(AddCustomerInput.class, Customer.class)
                .byDefault()
                .register();

        factory.classMap(Customer.class, GetCustomerOutput.class)
                .byDefault()
                .register();

        factory.classMap(Customer.class, AddCustomerOutput.class)
                .byDefault()
                .register();
    }
}
