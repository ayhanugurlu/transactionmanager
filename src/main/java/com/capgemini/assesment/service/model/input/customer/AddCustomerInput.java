package com.capgemini.assesment.service.model.input.customer;

/**
 * Created by ayhanugurlu on 5/26/18.
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCustomerInput {
    private String nationalityId;
    private String name;
    private String surname;

}
