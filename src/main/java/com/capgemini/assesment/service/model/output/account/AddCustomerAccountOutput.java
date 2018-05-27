package com.capgemini.assesment.service.model.output.account;

/**
 * Created by ayhanugurlu on 5/26/18.
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddCustomerAccountOutput {
    private long id;
    private String ownerId;
    private String currencyType;
    private long amount;
}
