package com.bank.bankproject.service.dto;


import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class LoanInstallmentDto implements Serializable {

    /*
     * Κωδικός κίνησης δανείου
     */
    private Long id;

    /*
     * Το δάνειο
     */
    private LoanDto loan;

    /*
     * Tο interest rate του δανείου * 100
     */
    private Double loanInterest;

    private Double loanCapital;

    /*
     *  Hμερομηνία έναρξης δόσεων
     */
    private LocalDate startDate;

    /*
     * Tο ποσό πληρωμής της δόσης δηλ. loan_interest + loan_capital
     */
    private Double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanInstallmentDto that = (LoanInstallmentDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
