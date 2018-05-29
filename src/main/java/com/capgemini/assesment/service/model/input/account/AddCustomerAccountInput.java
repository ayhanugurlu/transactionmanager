package com.capgemini.assesment.service.model.input.account;

import lombok.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AddCustomerAccountInput {
    private long ownerId;
    private String currencyType;
    private long amount;
}
