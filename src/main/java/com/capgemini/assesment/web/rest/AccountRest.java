package com.capgemini.assesment.web.rest;

import com.capgemini.assesment.service.AccountService;
import com.capgemini.assesment.service.exception.CustomerAlreadyExist;
import com.capgemini.assesment.service.exception.CustomerNotFound;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.web.rest.request.account.AddCustomerAccountRequest;
import com.capgemini.assesment.web.rest.response.account.AddCustomerAccountResponse;
import com.capgemini.assesment.web.rest.response.customer.AddCustomerResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@RestController
public class AccountRest {

    private static Logger logger = LoggerFactory.getLogger(AccountRest.class);



    @Autowired
    private Tracer tracer;

    @Autowired
    @Qualifier("customerRestMapper")
    private MapperFacade mapperFacade;

    @Autowired
    AccountService accountService;



    @ApiOperation(value = "add account for customer",
            notes = "add account for customer<br/>")
    @PostMapping("addAccount")
    public @ResponseBody
    AddCustomerAccountResponse addAccount(@ApiParam(value = "owner id, currency type, amount") @RequestBody AddCustomerAccountRequest addCustomerAccountRequest) throws CustomerAlreadyExist, CustomerNotFound {
        logger.debug("addAccount method start", tracer.getCurrentSpan().getTraceId());
        AddCustomerAccountInput input = mapperFacade.map(addCustomerAccountRequest, AddCustomerAccountInput.class);
        AddCustomerAccountOutput output = accountService.addAccount(input);
        AddCustomerAccountResponse addCustomerAccountResponse = mapperFacade.map(output,AddCustomerAccountResponse.class);
        logger.debug("addAccount method finis", tracer.getCurrentSpan().getTraceId());

        return addCustomerAccountResponse;
    }

}
