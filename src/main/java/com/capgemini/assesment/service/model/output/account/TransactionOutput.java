package com.capgemini.assesment.service.model.output.account;

import lombok.*;

import java.util.Date;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionOutput {

    private long amount;
    private Date transactionDate;

}
