package com.bank.bankproject.web.rest;


import com.bank.bankproject.domain.Loan;
import com.bank.bankproject.domain.UserRoleId;
import com.bank.bankproject.repository.UserRoleRepository;
import com.bank.bankproject.service.UserRoleService;
import com.bank.bankproject.service.dto.UserRoleDto;
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
@RequestMapping("api/admin")
public class UserRoleResource {

    private final Logger log = LoggerFactory.getLogger(UserRoleResource.class);

    private static final String ENTITY_NAME = "loan";

    private final UserRoleService userRoleService;
    private final UserRoleRepository userRoleRepository;


    public UserRoleResource(
            UserRoleService userRoleService,
            UserRoleRepository userRoleRepository
    ) {
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
    }

    /**
     * {@code POST  /user-role} : Create a new userRole.
     *
     * @param userRoleDto the userRoleDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userRoleDto, or with status {@code 400 (Bad Request)} if the loan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-role")
    public ResponseEntity<UserRoleDto> createLoan(@Valid @RequestBody UserRoleDto userRoleDto)
            throws URISyntaxException {
        log.debug("REST request to save Loan : {}", userRoleDto);
        if (userRoleDto.getUserRoleId() != null) {
            throw new BadRequestAlertException("A new Loan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRoleDto result = userRoleService.save(userRoleDto);
        return ResponseEntity
                .created(new URI("api/loan/" + result.getUserRoleId()))
                .body(result);
    }

    /**
     * {@code PUT  /user-role/:userId-roleId} : Updates an existing userRole.
     *
     * @param userId-roleId the id of the userRoleDto to save.
     * @param userRoleDto   the userRoleDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userRoleDto,
     * or with status {@code 400 (Bad Request)} if the userRoleDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userRoleDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-role/{userId}-{roleId}")
    public ResponseEntity<UserRoleDto> updateUserRole(
            @PathVariable(value = "userId", required = false) final Long userId,
            @PathVariable(value = "roleId", required = false) final Long roleId,
            @Valid @RequestBody UserRoleDto userRoleDto
    ) throws URISyntaxException {
        log.debug("REST request to update userRole : {},{}, {}", userId, roleId, userRoleDto);
        UserRoleId id = new UserRoleId(userId, roleId);
        if (userRoleDto.getUserRoleId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userRoleDto.getUserRoleId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userRoleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserRoleDto result = userRoleService.save(userRoleDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /user-role} : get all the userRoles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of loan in body.
     */
    @GetMapping("/user-role")
    public ResponseEntity<List<UserRoleDto>> getAllUserRoles(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all UserRoles ");
        Page<UserRoleDto> page = userRoleService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /user-role/:userId-roleId} : get the "id" userRole.
     *
     * @param userId-roleId the id of the userRoleDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userRoleDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-role/{userId}-{roleId}")
    public ResponseEntity<Optional<UserRoleDto>> getLoan(@PathVariable Long userId,
                                                         @PathVariable Long roleId) {
        log.debug("REST request to get loan : {},{}", userId, roleId);
        UserRoleId id = new UserRoleId(userId, roleId);
        Optional<UserRoleDto> userRoleDto = userRoleService.findOne(id);
        return ResponseEntity.ok().body(userRoleDto);
    }

    /**
     * {@code DELETE  /user-role/:userId-roleId} : delete the "id" userRole.
     *
     * @param userId-roleId the id of the userRoleDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-role/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long userId,
                                           @PathVariable Long roleId) {
        log.debug("REST request to delete UserRole : {},{}", userId, roleId);
        UserRoleId id = new UserRoleId(userId, roleId);
        userRoleService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
