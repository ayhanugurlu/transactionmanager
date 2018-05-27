package com.capgemini.assesment.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by ayhanugurlu on 5/27/18.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch= FetchType.LAZY)
    private Account account;
    private long amount;
    private TransactionStatus transactionStatus;
}
