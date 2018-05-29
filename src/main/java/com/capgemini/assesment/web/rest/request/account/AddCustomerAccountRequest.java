package com.capgemini.assesment.web.rest.request.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AddCustomerAccountRequest {
    private long ownerId;
    @ApiModelProperty(
        name = "currencyType",
        value = "Currency Type, ex: USD, EUR, TRY",
        required = true,
        example = "USD"
    )
    private String currencyType;
    private long amount;
}
