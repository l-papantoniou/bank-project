package com.bank.bankproject.service;


import com.bank.bankproject.domain.LoanInstallment;
import com.bank.bankproject.repository.LoanInstallmentRepository;
import com.bank.bankproject.service.dto.LoanInstallmentDto;
import com.bank.bankproject.service.mapper.LoanInstallmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link LoanInstallment}.
 */
@Service
@Transactional
public class LoanInstallmentService {

    private final Logger log = LoggerFactory.getLogger(LoanInstallmentService.class);

    private final LoanInstallmentRepository loanInstallmentRepository;
    private final LoanInstallmentMapper loanInstallmentMapper;

    public LoanInstallmentService(LoanInstallmentRepository loanInstallmentRepository, LoanInstallmentMapper loanInstallmentMapper) {
        this.loanInstallmentRepository = loanInstallmentRepository;
        this.loanInstallmentMapper = loanInstallmentMapper;
    }

    @Transactional
    public LoanInstallmentDto save(LoanInstallmentDto loanInstallmentDto) {
        log.debug("Request to save LoanInstallment : {}", loanInstallmentDto);
        LoanInstallment loanInstallment = loanInstallmentMapper.toEntity(loanInstallmentDto);
        loanInstallment = loanInstallmentRepository.save(loanInstallment);
        return loanInstallmentMapper.toDto(loanInstallment);
    }

    @Transactional
    public Optional<LoanInstallmentDto> partialUpdate(LoanInstallmentDto loanInstallmentDto) {
        log.debug("Request to partially update LoanInstallment : {}", loanInstallmentDto);

        return loanInstallmentRepository
                .findById(loanInstallmentDto.getId())
                .map(existingLoanInstallment -> {
                    loanInstallmentMapper.partialUpdate(existingLoanInstallment, loanInstallmentDto);

                    return existingLoanInstallment;
                })
                .map(loanInstallmentRepository::save)
                .map(loanInstallmentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LoanInstallmentDto> findAll(Pageable pageable) {
        log.debug("Request to get all LoanInstallment");
        return loanInstallmentRepository.findAll(pageable).map(loanInstallmentMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LoanInstallmentDto> findOne(Long id) {
        log.debug("Request to get LoanInstallment : {}", id);
        return loanInstallmentRepository.findById(id).map(loanInstallmentMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete LoanInstallment : {}", id);
        loanInstallmentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<LoanInstallment> findById(Long id) {
        log.debug("Request to get a LoanInstallment by its id : {}", id);
        return loanInstallmentRepository.findById(id);
    }

}
