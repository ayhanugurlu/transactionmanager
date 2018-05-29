package com.capgemini.assesment;

import com.capgemini.assesment.service.CustomerService;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class TransactionmanagerApplication {

	private static Logger logger = LoggerFactory.getLogger(TransactionmanagerApplication.class);

	@Autowired
	private Tracer tracer;

	@Autowired
	CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(TransactionmanagerApplication.class, args);
	}


	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		tracer.createSpan("init");
		AddCustomerInput addCustomerInput = AddCustomerInput.builder().name("name").surname("surname").nationalityId("tr").build();
		try {
			customerService.addCustomer(addCustomerInput);
		} catch (CustomerAlreadyExist customerAlreadyExist) {
			logger.error(customerAlreadyExist.getMessage());
		}
	}

}
