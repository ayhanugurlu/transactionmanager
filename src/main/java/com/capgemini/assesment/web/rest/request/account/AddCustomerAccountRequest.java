package com.capgemini.assesment.web.rest.request.account;

import lombok.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AddCustomerAccountRequest {
    private long ownerId;
    private String currencyType;
    private long amount;
}
