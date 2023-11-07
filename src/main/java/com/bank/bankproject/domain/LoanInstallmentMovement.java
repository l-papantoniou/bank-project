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

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan_installment_movement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoanInstallmentMovement implements Serializable {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_loan_installment_movement")
    @SequenceGenerator(name = "seq_loan_installment_movement", sequenceName = "seq_loan_installment_movement", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    /*
     * Αν είναι για επιστροφή ή για προσθήκη
     */
    @Column(name = "status", length = 300)
    private String status;

    /*
     *  Hμερομηνία καταχώρησης πληρωμής
     */
    @Column(name = "payment_date", length = 300)
    private LocalDate paymentDate;

    /*
     * Tο ποσό που πλήρωσε
     */
    @Column(name = "amount_paid", length = 300)
    private Double amountPaid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanInstallmentMovement that = (LoanInstallmentMovement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}