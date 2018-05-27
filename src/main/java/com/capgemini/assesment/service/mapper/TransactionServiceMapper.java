package com.capgemini.assesment.service.mapper;

import com.capgemini.assesment.data.entity.Account;
import com.capgemini.assesment.data.entity.Transaction;
import com.capgemini.assesment.service.model.input.account.AddCustomerAccountInput;
import com.capgemini.assesment.service.model.input.transaction.TransactionInput;
import com.capgemini.assesment.service.model.output.account.AddCustomerAccountOutput;
import com.capgemini.assesment.service.model.output.transaction.TransactionOutput;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class TransactionServiceMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {

        factory.classMap (TransactionInput.class, Transaction.class)
                .field("ownerId","customer.id")
                .byDefault()
                .register();
        factory.classMap (Transaction.class, TransactionOutput.class)
                .byDefault()
                .register();


    }
}
