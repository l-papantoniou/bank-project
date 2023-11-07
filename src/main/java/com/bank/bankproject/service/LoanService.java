package com.bank.bankproject.service;


import com.bank.bankproject.domain.Loan;
import com.bank.bankproject.repository.LoanRepository;
import com.bank.bankproject.service.dto.LoanDto;
import com.bank.bankproject.service.mapper.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link Loan}.
 */
@Service
@Transactional
public class LoanService {

    private final Logger log = LoggerFactory.getLogger(LoanService.class);

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Transactional
    public LoanDto save(LoanDto loanDto) {
        log.debug("Request to save Loan : {}", loanDto);
        Loan loan = loanMapper.toEntity(loanDto);
        loan = loanRepository.save(loan);
        return loanMapper.toDto(loan);
    }

    @Transactional
    public Optional<LoanDto> partialUpdate(LoanDto loanDto) {
        log.debug("Request to partially update Loan : {}", loanDto);

        return loanRepository
                .findById(loanDto.getId())
                .map(existingLoan -> {
                    loanMapper.partialUpdate(existingLoan, loanDto);

                    return existingLoan;
                })
                .map(loanRepository::save)
                .map(loanMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<LoanDto> findAll(Pageable pageable) {
        log.debug("Request to get all Loans");
        return loanRepository.findAll(pageable).map(loanMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<LoanDto> findOne(Long id) {
        log.debug("Request to get Loan : {}", id);
        return loanRepository.findById(id).map(loanMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete AsAssets : {}", id);
        loanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Loan> findById(Long id) {
        log.debug("Request to get a AsAsset by its id : {}", id);
        return loanRepository.findById(id);
    }

}
