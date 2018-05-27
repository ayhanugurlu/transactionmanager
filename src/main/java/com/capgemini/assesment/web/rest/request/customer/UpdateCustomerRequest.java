package com.capgemini.assesment.web.rest.request.customer;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@NoArgsConstructor
public class UpdateCustomerRequest {
    private String nationalityId;
    private String name;
    private String surname;

}
