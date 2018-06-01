package com.capgemini.assesment.web.rest;

import com.capgemini.assesment.service.TransactionService;
import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.transaction.TransactionResultOutput;
import com.capgemini.assesment.web.rest.request.transaction.TransactionRequest;
import com.capgemini.assesment.web.rest.response.transaction.TransactionResponse;
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
