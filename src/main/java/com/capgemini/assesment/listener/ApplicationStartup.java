package com.capgemini.assesment.listener;

import com.capgemini.assesment.service.CustomerService;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/30/18.
 */
@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    private static Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    @Autowired
    CustomerService customerService;
    @Autowired
    private Tracer tracer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        tracer.createSpan("init");
        AddCustomerInput addCustomerInput = AddCustomerInput.builder().name("name").surname("surname").nationalityId("tr").build();
        try {
            customerService.addCustomer(addCustomerInput);
        } catch (CustomerAlreadyExist customerAlreadyExist) {
            logger.error(customerAlreadyExist.getMessage());
        }
    }
}
