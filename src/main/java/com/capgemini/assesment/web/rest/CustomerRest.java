package com.capgemini.assesment.web.rest;

import com.capgemini.assesment.service.CustomerService;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import com.capgemini.assesment.web.rest.request.customer.AddCustomerRequest;
import com.capgemini.assesment.web.rest.response.customer.AddCustomerResponse;
import com.capgemini.assesment.web.rest.response.customer.GetCustomerResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@RestController
public class CustomerRest {

    private static Logger logger = LoggerFactory.getLogger(CustomerRest.class);

    @Autowired
    @Qualifier("customerRestMapper")
    private MapperFacade mapperFacade;

    @Autowired
    private Tracer tracer;

    @Autowired
    CustomerService customerService;


    @ApiOperation(value = "get customer ",
            notes = "Find customer use by natioanlity id and return it.<br/>")
    @GetMapping("getCustomer/{id}")
    public GetCustomerResponse getCustomer(@ApiParam(value = "nationality id")@PathVariable(name = "id") String nationalityId) throws CustomerNotFound {
        logger.debug("getCustomer method start", tracer.getCurrentSpan().getTraceId());
        GetCustomerOutput getCustomerOutput = customerService.getCustomerByNationalityId(nationalityId);
        GetCustomerResponse getCustomerResponse = mapperFacade.map(getCustomerOutput,GetCustomerResponse.class);
        logger.debug("getCustomer method finish", tracer.getCurrentSpan().getTraceId());
        return getCustomerResponse;
    }

    @ApiOperation(value = "add cutomer",
            notes = "add customer if it is not exist.<br/>")
    @PostMapping("addCustomer")
    public @ResponseBody AddCustomerResponse addCustomer(@ApiParam(value = "nationality id, name and surname") @RequestBody AddCustomerRequest addCustomerRequest) throws CustomerAlreadyExist {
        logger.debug("addCustomer method start", tracer.getCurrentSpan().getTraceId());
        AddCustomerInput input = mapperFacade.map(addCustomerRequest, AddCustomerInput.class);
        AddCustomerOutput output = customerService.addCustomer(input);
        AddCustomerResponse customerResponse = mapperFacade.map(output,AddCustomerResponse.class);
        logger.debug("addCustomer method finis", tracer.getCurrentSpan().getTraceId());

        return customerResponse;
    }

}
