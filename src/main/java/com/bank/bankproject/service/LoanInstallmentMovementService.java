package com.bank.bankproject.service;

import com.bank.bankproject.domain.LoanInstallmentMovement;
import com.bank.bankproject.repository.LoanInstallmentMovementRepository;
import com.bank.bankproject.service.dto.LoanInstallmentMovementDto;
import com.bank.bankproject.service.mapper.LoanInstallmentMovementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LoanInstallmentMovement}.
 */
@Service
@Transactional
public class LoanInstallmentMovementService {

    private final Logger log = LoggerFactory.getLogger(LoanInstallmentMovementService.class);

    private final LoanInstallmentMovementRepository loanInstallmentMovementRepository;
    private final LoanInstallmentMovementMapper loanInstallmentMovementMapper;


    public LoanInstallmentMovementService(LoanInstallmentMovementRepository loanInstallmentMovementRepository,
                                          LoanInstallmentMovementMapper loanInstallmentMovementMapper) {
        this.loanInstallmentMovementRepository = loanInstallmentMovementRepository;
        this.loanInstallmentMovementMapper = loanInstallmentMovementMapper;
    }

    @Transactional
    public LoanInstallmentMovementDto save(LoanInstallmentMovementDto loanInstallmentMovementDto) {
        log.debug("Request to save LoanInstallmentMovement : {}", loanInstallmentMovementDto);
        LoanInstallmentMovement loanInstallmentMovement = loanInstallmentMovementMapper.toEntity(loanInstallmentMovementDto);
        loanInstallmentMovement = loanInstallmentMovementRepository.save(loanInstallmentMovement);
        return loanInstallmentMovementMapper.toDto(loanInstallmentMovement);
    }

    @Transactional
    public Optional<LoanInstallmentMovementDto> partialUpdate(LoanInstallmentMovementDto loanInstallmentMovementDto) {
        log.debug("Request to partially update LoanInstallmentMovement : {}", loanInstallmentMovementDto);

        return loanInstallmentMovementRepository
                .findById(loanInstallmentMovementDto.getId())
                .map(existingLoanInstallmentMovement -> {
                    loanInstallmentMovementMapper.partialUpdate(existingLoanInstallmentMovement, loanInstallmentMovementDto);

                    return existingLoanInstallmentMovement;
                })
                .map(loanInstallmentMovementRepository::save)
                .map(loanInstallmentMovementMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LoanInstallmentMovementDto> findAll(Pageable pageable) {
        log.debug("Request to get all LoanInstallmentMovement");
        return loanInstallmentMovementRepository.findAll(pageable).map(loanInstallmentMovementMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LoanInstallmentMovementDto> findOne(Long id) {
        log.debug("Request to get LoanInstallmentMovement : {}", id);
        return loanInstallmentMovementRepository.findById(id).map(loanInstallmentMovementMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete LoanInstallmentMovement : {}", id);
        loanInstallmentMovementRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<LoanInstallmentMovement> findById(Long id) {
        log.debug("Request to get a LoanInstallmentMovement by its id : {}", id);
        return loanInstallmentMovementRepository.findById(id);
    }

}