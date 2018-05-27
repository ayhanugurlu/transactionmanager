package com.capgemini.assesment.service.model.output.customer;

/**
 * Created by ayhanugurlu on 5/26/18.
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AddCustomerOutput {
    private long id;
    private String nationalityId;
    private String name;
    private String surname;

}
