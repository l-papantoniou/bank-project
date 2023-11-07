package com.bank.bankproject.repository;

import com.bank.bankproject.domain.LoanInstallmentMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LoanInstallmentMovement entity.
 */
@Repository
public interface LoanInstallmentMovementRepository extends JpaRepository<LoanInstallmentMovement, Long>, JpaSpecificationExecutor<LoanInstallmentMovement> {
}
