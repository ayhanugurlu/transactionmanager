package com.capgemini.assesment.service.mapper;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Customer;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.customer.AddCustomerInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountOutput;
import com.capgemini.assesment.service.model.output.account.GetAccountTransactionOutput;
import com.capgemini.assesment.service.model.output.account.TransactionOutput;
import com.capgemini.assesment.service.model.output.customer.AddCustomerOutput;
import com.capgemini.assesment.service.model.output.customer.GetCustomerOutput;
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
