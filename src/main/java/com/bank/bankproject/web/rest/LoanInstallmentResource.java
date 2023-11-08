package com.bank.bankproject.web.rest;

import com.bank.bankproject.domain.LoanInstallment;
import com.bank.bankproject.repository.LoanInstallmentRepository;
import com.bank.bankproject.service.LoanInstallmentService;
import com.bank.bankproject.service.dto.LoanInstallmentDto;
import com.bank.bankproject.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link LoanInstallment}.
 */
@RestController
@RequestMapping("api")
public class LoanInstallmentResource {

    private final Logger log = LoggerFactory.getLogger(LoanInstallmentResource.class);

    private static final String ENTITY_NAME = "loanInstallment";

    private final LoanInstallmentService loanInstallmentService;
    private final LoanInstallmentRepository loanInstallmentRepository;


    public LoanInstallmentResource(
            LoanInstallmentService loanInstallmentService,
            LoanInstallmentRepository loanInstallmentRepository
    ) {
        this.loanInstallmentService = loanInstallmentService;
        this.loanInstallmentRepository = loanInstallmentRepository;
    }

    /**
     * {@code POST  /loan-installment} : Create a new loanInstallment.
     *
     * @param loanInstallmentDto the loanInstallmentDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanInstallmentDto, or with status {@code 400 (Bad Request)} if the loan-installment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-installment")
    public ResponseEntity<LoanInstallmentDto> createLoanInstallment(@Valid @RequestBody LoanInstallmentDto loanInstallmentDto)
            throws URISyntaxException {
        log.debug("REST request to save LoanInstallment : {}", loanInstallmentDto);
        if (loanInstallmentDto.getId() != null) {
            throw new BadRequestAlertException("A new Loan Installment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanInstallmentDto result = loanInstallmentService.save(loanInstallmentDto);
        return ResponseEntity
                .created(new URI("/api/loan/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /loan-installment/:loanId} : Updates an existing loan-installment.
     *
     * @param id  the id of the loanInstallmentDto to save.
     * @param loanInstallmentDto the loanInstallmentDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanInstallmentDto,
     * or with status {@code 400 (Bad Request)} if the loanInstallmentDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanInstallmentDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-installment/{id}")
    public ResponseEntity<LoanInstallmentDto> updateLoanInstallment(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody LoanInstallmentDto loanInstallmentDto
    ) throws URISyntaxException {
        log.debug("REST request to update Loan Installment : {}, {}", id, loanInstallmentDto);
        if (loanInstallmentDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanInstallmentDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanInstallmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanInstallmentDto result = loanInstallmentService.save(loanInstallmentDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /loan-installment} : get all the loanInstallment.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanInstallment in body.
     */
    @GetMapping("/loan-installment")
    public ResponseEntity<List<LoanInstallmentDto>> getAllLoanInstallments(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all Loan Installments");
        Page<LoanInstallmentDto> page = loanInstallmentService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /loan-installment/:id} : get the "id" loan-installment.
     *
     * @param id the id of the loanInstallmentDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanInstallmentDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-installment/{id}")
    public ResponseEntity<Optional<LoanInstallmentDto>> getLoanInstallment(@PathVariable Long id) {
        log.debug("REST request to get loanInstallment : {}", id);
        Optional<LoanInstallmentDto> loanInstallmentDto = loanInstallmentService.findOne(id);
        return ResponseEntity.ok().body(loanInstallmentDto);
    }

    /**
     * {@code DELETE  /loan-installment/:id} : delete the "id" loan-installment.
     *
     * @param id the id of the loanInstallmentDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-installment/{id}")
    public ResponseEntity<Void> deleteLoanInstallment(@PathVariable Long id) {
        log.debug("REST request to delete LoanInstallment : {}", id);
        loanInstallmentService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
