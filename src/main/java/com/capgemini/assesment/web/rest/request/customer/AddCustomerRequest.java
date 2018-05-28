package com.capgemini.assesment.web.rest.request.customer;

import lombok.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AddCustomerRequest {
    private String nationalityId;
    private String name;
    private String surname;

}
