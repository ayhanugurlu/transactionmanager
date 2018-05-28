package com.capgemini.assesment.web.rest.mapper;

import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import com.capgemini.assesment.web.rest.request.account.AddCustomerAccountRequest;
import com.capgemini.assesment.web.rest.request.transaction.TransactionRequest;
import com.capgemini.assesment.web.rest.response.account.AddCustomerAccountResponse;
import com.capgemini.assesment.web.rest.response.account.GetAccountTransactionResponse;
import com.capgemini.assesment.web.rest.response.transaction.TransactionResponse;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class TransactionRestMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {
        factory.classMap (TransactionRequest.class, TransactionInput.class)
                .byDefault()
                .register();

        factory.classMap (TransactionOutput.class, TransactionResponse.class)
                .byDefault()
                .register();

    }



}
