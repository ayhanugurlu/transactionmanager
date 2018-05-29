package com.capgemini.assesment.web.rest.request.transaction;

import lombok.*;

/**
 * Created by ayhanugurlu on 5/28/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {
    private long accountId;
    private long amount;
}
