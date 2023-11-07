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
public class LoanInstallmentMovementDto implements Serializable {

    private Long id;

    /*
     * Πελάτης
     */
    private CustomerDto customer;

    /*
     * Αν είναι για επιστροφή ή για προσθήκη
     */
    @Size(max = 300)
    private String status;

    /*
     *  Hμερομηνία καταχώρησης πληρωμής
     */
    @Size(max = 300)
    private LocalDate paymentDate;

    /*
     * Tο ποσό που πλήρωσε
     */
    private Double amountPaid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanInstallmentMovementDto that = (LoanInstallmentMovementDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
