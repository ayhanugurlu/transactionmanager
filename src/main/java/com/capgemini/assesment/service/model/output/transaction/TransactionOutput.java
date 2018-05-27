package com.capgemini.assesment.service.model.output.transaction;

import com.capgemini.assesment.data.entity.TransactionStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Setter
@Getter
@NoArgsConstructor
public class TransactionOutput {

    private TransactionStatus status;

}
