package com.au.example.service.mapper;

import com.au.example.data.entity.Account;
import com.au.example.service.model.output.account.GetAccountTransactionOutput;
import com.au.example.data.entity.Transaction;
import com.au.example.service.model.input.account.AddCustomerAccountInput;
import com.au.example.service.model.output.account.AddCustomerAccountOutput;
import com.au.example.service.model.output.account.GetAccountOutput;
import com.au.example.service.model.output.account.TransactionOutput;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class AccountServiceMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {

        factory.classMap(AddCustomerAccountInput.class, Account.class)
                .field("ownerId", "customer.id")
                .byDefault()
                .register();
        factory.classMap(Account.class, AddCustomerAccountOutput.class)
                .field("customer.id", "ownerId")
                .byDefault()
                .register();

        factory.classMap(Account.class, GetAccountTransactionOutput.class)
                .field("customer.name", "name")
                .field("customer.surname", "surname")
                .byDefault()
                .register();

        factory.classMap(Transaction.class, TransactionOutput.class).byDefault()
                .register();


        factory.classMap(Account.class, GetAccountOutput.class)
                .field("customer.id", "ownerId")
                .byDefault()
                .register();

    }
}
