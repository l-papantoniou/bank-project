package com.bank.bankproject.web.rest;

import com.bank.bankproject.domain.LoanInstallmentMovement;
import com.bank.bankproject.repository.LoanInstallmentMovementRepository;
import com.bank.bankproject.service.LoanInstallmentMovementService;
import com.bank.bankproject.service.dto.LoanInstallmentMovementDto;
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
 * REST controller for managing {@link LoanInstallmentMovement}.
 */
@RestController
@RequestMapping("/api")
public class LoanInstallmentMovementResource {

    private final Logger log = LoggerFactory.getLogger(LoanInstallmentMovementResource.class);

    private static final String ENTITY_NAME = "LoanInstallmentMovement";

    private final LoanInstallmentMovementService loanInstallmentMovementService;
    private final LoanInstallmentMovementRepository loanInstallmentMovementRepository;


    public LoanInstallmentMovementResource(
            LoanInstallmentMovementService loanInstallmentMovementService,
            LoanInstallmentMovementRepository loanInstallmentMovementRepository
    ) {
        this.loanInstallmentMovementService = loanInstallmentMovementService;
        this.loanInstallmentMovementRepository = loanInstallmentMovementRepository;
    }

    /**
     * {@code POST  /loan-installment-movement-movement} : Create a new loanInstallmentMovement.
     *
     * @param loanInstallmentMovementDto the loanInstallmentMovementDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanInstallmentMovementDto, or with status {@code 400 (Bad Request)} if the loan-installment-movement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan-installment-movement")
    public ResponseEntity<LoanInstallmentMovementDto> createLoanInstallment(@Valid @RequestBody LoanInstallmentMovementDto loanInstallmentMovementDto)
            throws URISyntaxException {
        log.debug("REST request to save LoanInstallmentMovement : {}", loanInstallmentMovementDto);
        if (loanInstallmentMovementDto.getId() != null) {
            throw new BadRequestAlertException("A new Loan Installment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanInstallmentMovementDto result = loanInstallmentMovementService.save(loanInstallmentMovementDto);
        return ResponseEntity
                .created(new URI("/api/loan-installment-movement/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /loan-installment-movement-movement/:id} : Updates an existing loan-installment-movement.
     *
     * @param id  the id of the loanInstallmentMovementDto to save.
     * @param loanInstallmentMovementDto the loanInstallmentMovementDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanInstallmentMovementDto,
     * or with status {@code 400 (Bad Request)} if the loanInstallmentMovementDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanInstallmentMovementDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan-installment-movement/{id}")
    public ResponseEntity<LoanInstallmentMovementDto> updateLoanInstallment(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody LoanInstallmentMovementDto loanInstallmentMovementDto
    ) throws URISyntaxException {
        log.debug("REST request to update LoanInstallmentMovement : {}, {}", id, loanInstallmentMovementDto);
        if (loanInstallmentMovementDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanInstallmentMovementDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanInstallmentMovementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanInstallmentMovementDto result = loanInstallmentMovementService.save(loanInstallmentMovementDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /loan-installment-movement} : get all the loanInstallment.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loanInstallment in body.
     */
    @GetMapping("/loan-installment-movement")
    public ResponseEntity<List<LoanInstallmentMovementDto>> getAllLoanInstallments(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all LoanInstallmentMovement");
        Page<LoanInstallmentMovementDto> page = loanInstallmentMovementService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /loan-installment-movement/:id} : get the "id" loan-installment-movement.
     *
     * @param id the id of the loanInstallmentMovementDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanInstallmentMovementDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan-installment-movement/{id}")
    public ResponseEntity<Optional<LoanInstallmentMovementDto>> getLoanInstallmentMovement(@PathVariable Long id) {
        log.debug("REST request to get LoanInstallmentMovement : {}", id);
        Optional<LoanInstallmentMovementDto> loanInstallmentMovementDto = loanInstallmentMovementService.findOne(id);
        return ResponseEntity.ok().body(loanInstallmentMovementDto);
    }

    /**
     * {@code DELETE  /loan-installment-movement/:id} : delete the "id" loan-installment-movement.
     *
     * @param id the id of the loanInstallmentMovementDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan-installment-movement/{id}")
    public ResponseEntity<Void> deleteLoanInstallmentMovement(@PathVariable Long id) {
        log.debug("REST request to delete LoanInstallmentMovement : {}", id);
        loanInstallmentMovementService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
