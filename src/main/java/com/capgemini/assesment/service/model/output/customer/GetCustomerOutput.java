package com.capgemini.assesment.service.model.output.customer;

/**
 * Created by ayhanugurlu on 5/26/18.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCustomerOutput {
    private long id;
    private String nationalityId;
    private String name;
    private String surname;

}
