package com.bank.bankproject.web.rest;

import com.bank.bankproject.domain.Customer;
import com.bank.bankproject.repository.CustomerRepository;
import com.bank.bankproject.service.CustomerService;
import com.bank.bankproject.service.dto.CustomerDto;
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
 * REST controller for managing {@link Customer}.
 */
@RestController
@RequestMapping("api")
public class CustomerResource {

    private final Logger log = LoggerFactory.getLogger(CustomerResource.class);

    private static final String ENTITY_NAME = "customer";

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;


    public CustomerResource(
            CustomerService customerService,
            CustomerRepository customerRepository
    ) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    /**
     * {@code POST  /customer} : Create a new customer.
     *
     * @param customerDto the customerDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new customerDto, or with status {@code 400 (Bad Request)} if the customer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto)
            throws URISyntaxException {
        log.debug("REST request to save Customer : {}", customerDto);
        if (customerDto.getId() != null) {
            throw new BadRequestAlertException("A new Customer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustomerDto result = customerService.save(customerDto);
        return ResponseEntity
                .created(new URI("/api/employee/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /customer/:customerId} : Updates an existing Customer.
     *
     * @param customerId  the id of the customerDto to save.
     * @param customerDto the customerDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated customerDto,
     * or with status {@code 400 (Bad Request)} if the customerDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the customerDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomers(
            @PathVariable(value = "customerId", required = false) final Long customerId,
            @Valid @RequestBody CustomerDto customerDto
    ) throws URISyntaxException {
        log.debug("REST request to update AsCategory : {}, {}", customerId, customerDto);
        if (customerDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(customerId, customerDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!customerRepository.existsById(customerId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustomerDto result = customerService.save(customerDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /customer} : get all the customer.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of customer in body.
     */
    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDto>> getAllCustomers(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all Customers ");
        Page<CustomerDto> page = customerService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /customer/:id} : get the "id" customer.
     *
     * @param id the id of the customerDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the customerDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<Optional<CustomerDto>> getCustomer(@PathVariable Long id) {
        log.debug("REST request to get customer : {}", id);
        Optional<CustomerDto> customerDto = customerService.findOne(id);
        return ResponseEntity.ok().body(customerDto);
    }

    /**
     * {@code DELETE  /customer/:id} : delete the "id" customer.
     *
     * @param id the id of the customerDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        log.debug("REST request to delete Customer : {}", id);
        customerService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
