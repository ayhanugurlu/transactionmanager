package com.au.example.service.mapper;

import com.au.example.data.entity.Transaction;
import com.au.example.service.model.input.transaction.TransactionInput;
import com.au.example.service.model.output.transaction.TransactionResultOutput;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Component
public class TransactionServiceMapper extends ConfigurableMapper {


    protected void configure(MapperFactory factory) {

        factory.classMap(TransactionInput.class, Transaction.class)
                .field("accountId", "account.id")
                .byDefault()
                .register();
        factory.classMap(Transaction.class, TransactionResultOutput.class)
                .byDefault()
                .register();


    }
}
