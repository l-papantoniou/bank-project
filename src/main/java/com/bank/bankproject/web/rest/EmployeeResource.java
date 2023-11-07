package com.bank.bankproject.web.rest;


import com.bank.bankproject.domain.Employee;
import com.bank.bankproject.repository.EmployeeRepository;
import com.bank.bankproject.service.EmployeeService;
import com.bank.bankproject.service.dto.EmployeeDto;
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
 * REST controller for managing {@link Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employee";

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;


    public EmployeeResource(
            EmployeeService employeeService,
            EmployeeRepository employeeRepository
    ) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@code POST  /employee} : Create a new employee.
     *
     * @param employeeDto the employeeDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDto, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto)
            throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDto);
        if (employeeDto.getId() != null) {
            throw new BadRequestAlertException("A new Employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeDto result = employeeService.save(employeeDto);
        return ResponseEntity
                .created(new URI("/api/employee/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /employee/:employeeId} : Updates an existing Employee.
     *
     * @param employeeId  the id of the employeeDto to save.
     * @param employeeDto the employeeDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDto,
     * or with status {@code 400 (Bad Request)} if the employeeDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployees(
            @PathVariable(value = "employeeId", required = false) final Long employeeId,
            @Valid @RequestBody EmployeeDto employeeDto
    ) throws URISyntaxException {
        log.debug("REST request to update EMPLOYEE : {}, {}", employeeId, employeeDto);
        if (employeeDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(employeeId, employeeDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRepository.existsById(employeeId)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeDto result = employeeService.save(employeeDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /employee} : get all the employee.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employee in body.
     */
    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all Employees ");
        Page<EmployeeDto> page = employeeService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /employee/:id} : get the "id" employee.
     *
     * @param id the id of the employeeDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<Optional<EmployeeDto>> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get employee : {}", id);
        Optional<EmployeeDto> employeeDto = employeeService.findOne(id);
        return ResponseEntity.ok().body(employeeDto);
    }

    /**
     * {@code DELETE  /employee/:id} : delete the "id" employee.
     *
     * @param id the id of the employeeDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
