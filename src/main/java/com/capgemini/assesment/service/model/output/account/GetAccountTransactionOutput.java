package com.capgemini.assesment.service.model.output.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Ayhan.Ugurlu on 28/05/2018
 */
@Setter
@Getter
@NoArgsConstructor
public class GetAccountTransactionOutput {
    private String name;
    private String surname;
    private long balance;
    private List<TransactionOutput> transactionOutputs;
}
