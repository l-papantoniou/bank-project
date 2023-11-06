package com.bank.bankproject.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer implements Serializable {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer")
    @SequenceGenerator(name = "seq_customer", sequenceName = "seq_customer", allocationSize = 1)
    @Column(name = "id")
    private Long id;


    @Column(name = "name", length = 300)
    private String name;

    @Column(name = "surname", length = 300)
    private String surName;

    @Column(name = "afm", length = 300)
    private String afm;

    @Column(name = "amka", length = 300)
    private String amka;

    @Column(name = "phone", length = 300)
    private String phone;

    @Column(name = "date_of_birth")
    private LocalDate birthDate;

    @Column(name = "place_of_birth", length = 300)
    private String placeOfBirth;

    @Column(name = "address", length = 300)
    private String address;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
