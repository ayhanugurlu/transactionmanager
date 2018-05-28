package com.capgemini.assesment.service.model.input.transaction;

import lombok.*;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionInput {

    private long accountId;
    private long amount;
}
