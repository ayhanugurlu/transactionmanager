package com.capgemini.assesment.service.model.input.customer;

/**
 * Created by ayhanugurlu on 5/26/18.
 */

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AddCustomerInput {
    private String nationalityId;
    private String name;
    private String surname;

}
