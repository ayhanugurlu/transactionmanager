package com.capgemini.assesment.web.rest.mapper;

import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
import com.capgemini.assesment.web.rest.request.account.AddCustomerAccountRequest;
import com.capgemini.assesment.web.rest.request.customer.AddCustomerRequest;
import com.capgemini.assesment.web.rest.response.account.AddCustomerAccountResponse;
import com.capgemini.assesment.web.rest.response.account.GetAccountTransactionResponse;
import com.capgemini.assesment.web.rest.response.account.TransactionResponse;
import com.capgemini.assesment.web.rest.response.customer.AddCustomerResponse;
import com.capgemini.assesment.web.rest.response.customer.GetCustomerResponse;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class AccountRestMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {
        factory.classMap (AddCustomerAccountRequest.class, AddCustomerAccountInput.class)
                .byDefault()
                .register();

        factory.classMap (AddCustomerAccountOutput.class, AddCustomerAccountResponse.class)
                .byDefault()
                .register();

        factory.classMap (GetAccountTransactionOutput.class, GetAccountTransactionResponse.class)
                .byDefault()
                .register();

        factory.classMap(TransactionOutput.class, TransactionResponse.class).byDefault()
                .register();
    }



}
