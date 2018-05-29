package com.capgemini.assesment.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Account {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne(fetch=FetchType.EAGER)
    private Customer customer;
    @OneToMany(fetch=FetchType.LAZY, mappedBy = "account")
    private Set<Transaction> transactions;
    private String currencyType;
    private long balance;

}
