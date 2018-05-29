package com.capgemini.assesment.service.model.output.account;

import lombok.*;

/**
 * Created by ayhanugurlu on 5/29/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GetAccountOutput {

    private long id;
    private long ownerId;
    private String currencyType;
    private long balance;
}
