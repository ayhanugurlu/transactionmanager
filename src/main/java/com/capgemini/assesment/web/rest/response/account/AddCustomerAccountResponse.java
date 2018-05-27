package com.capgemini.assesment.web.rest.response.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class AddCustomerAccountResponse {
    private long id;
    private String ownerId;
    private String currencyType;
    private long amount;

}
