package com.bank.bankproject.web.rest;


import com.bank.bankproject.domain.Loan;
import com.bank.bankproject.repository.LoanRepository;
import com.bank.bankproject.service.LoanService;
import com.bank.bankproject.service.dto.LoanDto;
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
 * REST controller for managing {@link Loan}.
 */
@RestController
@RequestMapping("/api")
public class LoanResource {

    private final Logger log = LoggerFactory.getLogger(LoanResource.class);

    private static final String ENTITY_NAME = "loan";

    private final LoanService loanService;
    private final LoanRepository loanRepository;


    public LoanResource(
            LoanService loanService,
            LoanRepository loanRepository
    ) {
        this.loanService = loanService;
        this.loanRepository = loanRepository;
    }

    /**
     * {@code POST  /loan} : Create a new loan.
     *
     * @param loanDto the loanDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new loanDto, or with status {@code 400 (Bad Request)} if the loan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/loan")
    public ResponseEntity<LoanDto> createLoan(@Valid @RequestBody LoanDto loanDto)
            throws URISyntaxException {
        log.debug("REST request to save Loan : {}", loanDto);
        if (loanDto.getId() != null) {
            throw new BadRequestAlertException("A new Loan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoanDto result = loanService.save(loanDto);
        return ResponseEntity
                .created(new URI("/api/loan/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /loan/:id} : Updates an existing Loan.
     *
     * @param id  the id of the loanDto to save.
     * @param loanDto the loanDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated loanDto,
     * or with status {@code 400 (Bad Request)} if the loanDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the loanDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/loan/{id}")
    public ResponseEntity<LoanDto> updateLoans(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody LoanDto loanDto
    ) throws URISyntaxException {
        log.debug("REST request to update Loan : {}, {}", id, loanDto);
        if (loanDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, loanDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!loanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LoanDto result = loanService.save(loanDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /loan} : get all the loan.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loan in body.
     */
    @GetMapping("/loan")
    public ResponseEntity<List<LoanDto>> getAllLoans(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all Loans ");
        Page<LoanDto> page = loanService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /loan/:id} : get the "id" loan.
     *
     * @param id the id of the loanDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the loanDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/loan/{id}")
    public ResponseEntity<Optional<LoanDto>> getLoan(@PathVariable Long id) {
        log.debug("REST request to get loan : {}", id);
        Optional<LoanDto> loanDto = loanService.findOne(id);
        return ResponseEntity.ok().body(loanDto);
    }

    /**
     * {@code DELETE  /loan/:id} : delete the "id" loan.
     *
     * @param id the id of the loanDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/loan/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        log.debug("REST request to delete Loan : {}", id);
        loanService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
