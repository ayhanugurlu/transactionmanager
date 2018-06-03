package com.au.example.web.rest;

import com.au.example.service.TransactionService;
import com.au.example.service.exception.AccountNotFound;
import com.au.example.service.model.input.transaction.TransactionInput;
import com.au.example.web.rest.request.transaction.TransactionRequest;
import com.au.example.web.rest.response.transaction.TransactionResponse;
import com.au.example.service.exception.InsufficientBalance;
import com.au.example.service.model.output.transaction.TransactionResultOutput;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@Slf4j
@RestController
public class TransactionRest {


    @Autowired
    TransactionService transactionService;
    @Autowired
    private Tracer tracer;
    @Autowired
    @Qualifier("transactionRestMapper")
    private MapperFacade mapperFacade;

    @ApiOperation(value = "do transaction ",
            notes = "if account is avaliable fo transaction .<br/>")
    @PostMapping("doTransaction")
    public TransactionResponse doTransaction(@ApiParam(value = "account id and amount") @RequestBody TransactionRequest transactionRequest) throws AccountNotFound, InsufficientBalance {
        log.debug("doTransaction method start", tracer.getCurrentSpan().getTraceId());
        TransactionInput transactionInput = mapperFacade.map(transactionRequest, TransactionInput.class);
        TransactionResultOutput output = transactionService.doTransaction(transactionInput);
        TransactionResponse transactionResponse = mapperFacade.map(output, TransactionResponse.class);
        log.debug("doTransaction method finish", tracer.getCurrentSpan().getTraceId());
        return transactionResponse;

    }
}
