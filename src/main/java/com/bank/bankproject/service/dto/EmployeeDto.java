package com.bank.bankproject.service.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDto implements Serializable {

    /*
     * Κωδικός υπαλλήλου
     */
    private Long id;

    /*
     * Όνομα υπαλλήλου
     */
    @Size(max = 300)
    private String name;

    /*
     * Επώνυμο υπαλλήλου
     */
    @Size(max = 300)
    private String surName;

    /*
     * Α.Φ.Μ υπαλλήλου
     */
    @Size(max = 300)
    private String afm;

    /*
     * Α.Μ.Κ.Α υπαλλήλου
     */
    @Size(max = 300)
    private String amka;

    /*
     * τηλέφωνο υπαλλήλου
     */
    @Size(max = 300)
    private String phone;

    /*
     * email υπαλλήλου
     */
    @Size(max = 300)
    private String email;


    /*
     * Ημ. γέννησης υπαλλήλου
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

    /*
     * Ημ. έναρξης
     */
    @Size(max = 300)
    private String startDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
