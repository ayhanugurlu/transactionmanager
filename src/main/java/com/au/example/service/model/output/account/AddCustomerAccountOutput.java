package com.au.example.service.model.output.account;

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
    private long ownerId;
    private String currencyType;
}
