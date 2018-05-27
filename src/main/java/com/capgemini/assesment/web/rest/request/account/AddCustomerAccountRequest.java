package com.capgemini.assesment.web.rest.request.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class AddCustomerAccountRequest {
    private long ownerId;
    private String currencyType;
}
