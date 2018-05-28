package com.capgemini.assesment.web.rest.response.account;

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
public class GetAccountTransactionResponse {
    private String name;
    private String surname;
    private long balance;
    private List<TransactionResponse> transactionResponses;
}