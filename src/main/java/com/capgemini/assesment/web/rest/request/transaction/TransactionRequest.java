package com.capgemini.assesment.web.rest.request.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class TransactionRequest {
    private long accountId;
    private long amount;
}
