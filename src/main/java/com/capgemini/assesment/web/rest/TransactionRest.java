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
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@RestController
public class TransactionRest {

    private static Logger logger = LoggerFactory.getLogger(TransactionRest.class);


    @Autowired
    private Tracer tracer;

    @Autowired
    @Qualifier("transactionRestMapper")
    private MapperFacade mapperFacade;

    @Autowired
    TransactionService transactionService;

    @ApiOperation(value = "do transaction ",
            notes = "if account is avaliable fo transaction .<br/>")
    @PostMapping("doTransaction")
    public TransactionResponse doTransaction(@ApiParam(value = "account id and amount") @RequestBody TransactionRequest transactionRequest) throws AccountNotFound, InsufficientBalance {
        logger.debug("doTransaction method start", tracer.getCurrentSpan().getTraceId());
        TransactionInput transactionInput = mapperFacade.map(transactionRequest, TransactionInput.class);
        TransactionResultOutput output = transactionService.doTransaction(transactionInput);
        TransactionResponse transactionResponse = mapperFacade.map(output, TransactionResponse.class);
        logger.debug("doTransaction method finish", tracer.getCurrentSpan().getTraceId());
        return transactionResponse;

    }
}
