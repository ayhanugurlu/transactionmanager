package com.au.example.web.rest.response.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class GetCustomerResponse {
    private long id;
    private String nationalityId;
    private String name;
    private String surname;

}
