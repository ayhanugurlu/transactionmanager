package com.au.example.web.rest.mapper;

import com.au.example.service.model.input.account.AddCustomerAccountInput;
import com.au.example.service.model.output.account.AddCustomerAccountOutput;
import com.au.example.service.model.output.account.GetAccountTransactionOutput;
import com.au.example.web.rest.request.account.AddCustomerAccountRequest;
import com.au.example.web.rest.response.account.AddCustomerAccountResponse;
import com.au.example.web.rest.response.account.GetAccountTransactionResponse;
import com.au.example.service.model.output.account.TransactionOutput;
import com.au.example.web.rest.response.account.TransactionResponse;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class AccountRestMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {
        factory.classMap(AddCustomerAccountRequest.class, AddCustomerAccountInput.class)
                .byDefault()
                .register();

        factory.classMap(AddCustomerAccountOutput.class, AddCustomerAccountResponse.class)
                .byDefault()
                .register();

        factory.classMap(GetAccountTransactionOutput.class, GetAccountTransactionResponse.class)
                .byDefault()
                .register();

        factory.classMap(TransactionOutput.class, TransactionResponse.class).byDefault()
                .register();
    }


}
