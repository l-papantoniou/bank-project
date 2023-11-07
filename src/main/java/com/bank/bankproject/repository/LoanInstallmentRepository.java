package com.bank.bankproject.repository;

import com.bank.bankproject.domain.LoanInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LoanInstallment entity.
 */
@Repository
public interface LoanInstallmentRepository extends JpaRepository<LoanInstallment, Long>, JpaSpecificationExecutor<LoanInstallment> {
}
