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
import java.util.Objects;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Loan implements Serializable {


    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_loan")
    @SequenceGenerator(name = "seq_loan", sequenceName = "seq_loan", allocationSize = 1)
    private Long id;

    /*
     * Τύπος Δανείου
     */
    @Column(name = "name", length = 300)
    private String loanType;

    /*
     * Ο πελάτης που έχει λάβει το δάνειο
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*
     * Ο υπάλληλος που είναι υπεύθυνος για το δάνειο
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    /*
     * Οι μήνες που διαρκεί το δάνειο
     */
    @Column(name = "months")
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
    @Column(name = "status", length = 300)
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
