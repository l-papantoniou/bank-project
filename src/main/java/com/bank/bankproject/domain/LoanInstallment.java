package com.bank.bankproject.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/*
 * Δόσεις Δανείων
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_installment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoanInstallment implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_loan_installment")
    @SequenceGenerator(name = "seq_loan_installment", sequenceName = "seq_loan_installment", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    /*
     * Tο interest rate του δανείου * 100
     */
    @Column(name = "loan_interest")
    private Double loanInterest;

    @Column(name = "loan_capital")
    private Double loanCapital;

    /*
     *  Hμερομηνία έναρξης δόσεων
     */
    @Column(name = "start_date", length = 300)
    private LocalDate startDate;

    /*
     * Tο ποσό πληρωμής της δόσης δηλ. loan_interest + loan_capital
     */
    @Column(name = "amount", length = 300)
    private Double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanInstallment that = (LoanInstallment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
