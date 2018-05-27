package com.capgemini.assesment.service.model.input.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class AddCustomerAccountInput {
    private long ownerId;
    private String currencyType;
}
