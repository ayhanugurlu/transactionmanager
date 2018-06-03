package com.au.example.web.rest;

import com.au.example.service.CustomerService;
import com.au.example.service.exception.CustomerAlreadyExist;
import com.au.example.service.exception.CustomerNotFound;
import com.au.example.service.model.input.customer.AddCustomerInput;
import com.au.example.service.model.output.customer.AddCustomerOutput;
import com.au.example.service.model.output.customer.GetCustomerOutput;
import com.au.example.web.rest.request.customer.AddCustomerRequest;
import com.au.example.web.rest.response.customer.AddCustomerResponse;
import com.au.example.web.rest.response.customer.GetCustomerResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Slf4j
@RestController
public class CustomerRest {

    @Autowired
    CustomerService customerService;
    @Autowired
    @Qualifier("customerRestMapper")
    private MapperFacade mapperFacade;
    @Autowired
    private Tracer tracer;

    @ApiOperation(value = "get customer ",
            notes = "Find customer use by natioanlity id and return it.<br/>")
    @GetMapping("getCustomer/{id}")
    public GetCustomerResponse getCustomer(@ApiParam(value = "nationality id") @PathVariable(name = "id") String nationalityId) throws CustomerNotFound {
        log.debug("getCustomer method start", tracer.getCurrentSpan().getTraceId());
        GetCustomerOutput getCustomerOutput = customerService.getCustomerByNationalityId(nationalityId);
        GetCustomerResponse getCustomerResponse = mapperFacade.map(getCustomerOutput, GetCustomerResponse.class);
        log.debug("getCustomer method finish", tracer.getCurrentSpan().getTraceId());
        return getCustomerResponse;
    }

    @ApiOperation(value = "add cutomer",
            notes = "add customer if it is not exist.<br/>")
    @PostMapping("addCustomer")
    public @ResponseBody
    AddCustomerResponse addCustomer(@ApiParam(value = "nationality id, name and surname") @RequestBody AddCustomerRequest addCustomerRequest) throws CustomerAlreadyExist {
        log.debug("addCustomer method start", tracer.getCurrentSpan().getTraceId());
        AddCustomerInput input = mapperFacade.map(addCustomerRequest, AddCustomerInput.class);
        AddCustomerOutput output = customerService.addCustomer(input);
        AddCustomerResponse customerResponse = mapperFacade.map(output, AddCustomerResponse.class);
        log.debug("addCustomer method finis", tracer.getCurrentSpan().getTraceId());

        return customerResponse;
    }

}
