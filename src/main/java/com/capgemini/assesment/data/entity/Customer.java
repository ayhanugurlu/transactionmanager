package com.capgemini.assesment.data.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by ayhanugurlu on 5/26/18.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
public class Customer {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String nationalityId;
    private String name;
    private String surname;

}
