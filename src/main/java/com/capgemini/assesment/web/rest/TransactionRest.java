package com.capgemini.assesment.web.rest;

import com.capgemini.assesment.service.exception.AccountNotFound;
import com.capgemini.assesment.service.exception.InsufficientBalance;
import com.capgemini.assesment.web.rest.request.transaction.TransactionRequest;
import com.capgemini.assesment.web.rest.response.transaction.TransactionResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@RestController
public class TransactionRest {



    @ApiOperation(value = "do transaction ",
            notes = "if .<br/>")
    @PostMapping("getCustomer/{id}")
    public TransactionResponse doTransaction(TransactionRequest transactionInput) throws AccountNotFound, InsufficientBalance {
        return null;
    }
}
