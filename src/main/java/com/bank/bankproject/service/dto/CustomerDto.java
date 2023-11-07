package com.bank.bankproject.service.dto;

import com.bank.bankproject.domain.Customer;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link Customer} entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDto implements Serializable {


    /*
     *  Κωδικός πελάτη
     */
    private Long id;

    /*
     * Όνομα πελάτη
     */
    @Size(max = 300)
    private String name;

    /*
     * Επώνυμο πελάτη
     */
    @Size(max = 300)
    private String surName;

    /*
     * Α.Φ.Μ πελάτη
     */
    @Size(max = 300)
    private String afm;

    /*
     * Α.Μ.Κ.Α πελάτη
     */
    @Size(max = 300)
    private String amka;

    /*
     * Τηλέφωνο πελάτη
     */
    @Size(max = 300)
    private String phone;

    /*
     * Ημ. γέννησης πελάτη
     */
    private LocalDate birthDate;

    /*
     * Τόπος γέννησης
     */
    @Size(max = 300)
    private String placeOfBirth;

    /*
     * Διεύθυνση
     */
    @Size(max = 300)
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
