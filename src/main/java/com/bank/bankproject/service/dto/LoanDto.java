package com.bank.bankproject.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanDto implements Serializable {

    /*
     * Κωδικός δανείου
     */
    private Long id;

    /*
     * Τύπος Δανείου
     */
    @Column(name = "name", length = 300)
    private String loanType;

    /*
     * Ο πελάτης που έχει λάβει το δάνειο
     */
    private CustomerDto customer;

    /*
     * Ο υπάλληλος που είναι υπεύθυνος για το δάνειο
     */
    private EmployeeDto employee;

    /*
     * Οι μήνες που διαρκεί το δάνειο
     */
    private Integer months;

    /*
     * Το ποσό που απαιτεί ο πελάτης
     */
    @Column(name = "amount")
    private Double amount;

    /*
     * Επιτόκιο δανείου
     */
    @Column(name = "interest_rate")
    private Double interestRate;

    /*
     * Το ποσό που θα πάρει αρχικά απο την τράπεζα (στην αρχή είναι null)
     */
    @Column(name = "nominal_amount")
    private Double nominalAmount;

    /*
     * Στάτους αίτησης δανείου
     */
    @Size(max = 300)
    private String status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanDto loanDto = (LoanDto) o;
        return Objects.equals(id, loanDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
