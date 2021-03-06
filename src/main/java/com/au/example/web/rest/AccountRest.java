package com.au.example.web.rest;

import com.au.example.service.AccountService;
import com.au.example.service.exception.AccountNotFound;
import com.au.example.service.exception.CustomerNotFound;
import com.au.example.service.exception.InsufficientBalance;
import com.au.example.service.model.input.account.AddCustomerAccountInput;
import com.au.example.service.model.output.account.AddCustomerAccountOutput;
import com.au.example.service.model.output.account.GetAccountTransactionOutput;
import com.au.example.web.rest.request.account.AddCustomerAccountRequest;
import com.au.example.web.rest.response.account.AddCustomerAccountResponse;
import com.au.example.web.rest.response.account.GetAccountTransactionResponse;
import com.au.example.web.rest.response.account.TransactionResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Slf4j
@RestController
public class AccountRest {


    @Autowired
    AccountService accountService;
    @Autowired
    private Tracer tracer;
    @Autowired
    @Qualifier("customerRestMapper")
    private MapperFacade mapperFacade;

    @ApiOperation(value = "add account for customer",
            notes = "add account for customer<br/>")
    @PostMapping("addAccount")
    public @ResponseBody
    AddCustomerAccountResponse addAccount(@ApiParam(value = "owner id, currency type, amount") @RequestBody AddCustomerAccountRequest addCustomerAccountRequest) throws CustomerNotFound, AccountNotFound, InsufficientBalance {
        log.debug("addAccount method start", tracer.getCurrentSpan().getTraceId());
        AddCustomerAccountInput input = mapperFacade.map(addCustomerAccountRequest, AddCustomerAccountInput.class);
        AddCustomerAccountOutput output = accountService.addAccount(input);
        AddCustomerAccountResponse addCustomerAccountResponse = mapperFacade.map(output, AddCustomerAccountResponse.class);
        log.debug("addAccount method finish", tracer.getCurrentSpan().getTraceId());
        return addCustomerAccountResponse;
    }


    @ApiOperation(value = "get account transactions",
            notes = "get account transactions<br/>")
    @GetMapping("getAccountTransactions/{id}")
    public @ResponseBody
    GetAccountTransactionResponse getAccountTransactions(@ApiParam(value = "Account id") @PathVariable(name = "id") long accountId) throws AccountNotFound {
        log.debug("getAccountTransactions method start", tracer.getCurrentSpan().getTraceId());
        GetAccountTransactionOutput output = accountService.getAccountTransactions(accountId);
        GetAccountTransactionResponse getAccountTransactionOutput = mapperFacade.map(output, GetAccountTransactionResponse.class);
        Optional.ofNullable(output).ifPresent(getAccountTransactionOutput1 -> Optional.ofNullable(getAccountTransactionOutput1.getTransactionOutputs()).ifPresent(transactionOutputs -> {
            getAccountTransactionOutput.setTransactionResponses(transactionOutputs.stream().map(transactionOutput -> mapperFacade.map(transactionOutput, TransactionResponse.class)).collect(Collectors.toList()));
        }));

        log.debug("getAccountTransactions method finish", tracer.getCurrentSpan().getTraceId());
        return getAccountTransactionOutput;

    }

}
