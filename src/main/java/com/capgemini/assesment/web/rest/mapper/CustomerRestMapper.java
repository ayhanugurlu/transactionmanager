package com.capgemini.assesment.web.rest.mapper;

import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import com.capgemini.assesment.web.rest.request.customer.AddCustomerRequest;
import com.capgemini.assesment.web.rest.response.customer.AddCustomerResponse;
import com.capgemini.assesment.web.rest.response.customer.GetCustomerResponse;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class CustomerRestMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {
        factory.classMap (AddCustomerRequest.class, AddCustomerInput.class)
                .byDefault()
                .register();

        factory.classMap (GetCustomerOutput.class, GetCustomerResponse.class)
                .byDefault()
                .register();


        factory.classMap (AddCustomerOutput.class, AddCustomerResponse.class)

                .field("id", "customerId")
                .byDefault()
                .register();

    }



}
