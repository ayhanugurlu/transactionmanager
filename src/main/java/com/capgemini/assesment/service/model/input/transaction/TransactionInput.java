package com.capgemini.assesment.service.model.input.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class TransactionInput {

    private long accountId;
    private long amount;
}
