package com.capgemini.assesment.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch=FetchType.LAZY)
    private Customer customer;
    private String currencyType;
    private long amount;

}
