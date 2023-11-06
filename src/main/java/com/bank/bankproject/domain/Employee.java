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
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_employee")
    @SequenceGenerator(name = "seq_employee", sequenceName = "seq_employee", allocationSize = 1)
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

    @Column(name = "email", length = 300)
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate birthDate;

    @Column(name = "place_of_birth", length = 300)
    private String placeOfBirth;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "start_date", length = 300)
    private String startDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
