package com.bank.bankproject.web.rest;


import com.bank.bankproject.domain.Role;
import com.bank.bankproject.repository.RoleRepository;
import com.bank.bankproject.service.RoleService;
import com.bank.bankproject.service.dto.RoleDto;
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
 * REST controller for managing {@link Role}.
 */
@RestController
@RequestMapping("api/admin")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "role";

    private final RoleService roleService;
    private final RoleRepository roleRepository;


    public RoleResource(
            RoleService roleService,
            RoleRepository roleRepository
    ) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    /**
     * {@code POST  /role} : Create a new role.
     *
     * @param roleDto the roleDto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleDto, or with status {@code 400 (Bad Request)} if the loan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role")
    public ResponseEntity<RoleDto> createRole(@Valid @RequestBody RoleDto roleDto)
            throws URISyntaxException {
        log.debug("REST request to save Role : {}", roleDto);
        if (roleDto.getId() != null) {
            throw new BadRequestAlertException("A new Role cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleDto result = roleService.save(roleDto);
        return ResponseEntity
                .created(new URI("api/role/" + result.getId()))
                .body(result);
    }

    /**
     * {@code PUT  /role/:id} : Updates an existing Role.
     *
     * @param id  the id of the roleDto to save.
     * @param roleDto the roleDto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleDto,
     * or with status {@code 400 (Bad Request)} if the roleDto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleDto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role/{id}")
    public ResponseEntity<RoleDto> updateRole(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody RoleDto roleDto
    ) throws URISyntaxException {
        log.debug("REST request to update role : {}, {}", id, roleDto);
        if (roleDto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleDto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleDto result = roleService.save(roleDto);
        return ResponseEntity
                .ok()
                .body(result);
    }


    /**
     * {@code GET  /role} : get all the role.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of role in body.
     */
    @GetMapping("/role")
    public ResponseEntity<List<RoleDto>> getAllRoles(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get all Roles ");
        Page<RoleDto> page = roleService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /role/:id} : get the "id" role.
     *
     * @param id the id of the roleDto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleDto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role/{id}")
    public ResponseEntity<Optional<RoleDto>> getRole(@PathVariable Long id) {
        log.debug("REST request to get role : {}", id);
        Optional<RoleDto> roleDto = roleService.findOne(id);
        return ResponseEntity.ok().body(roleDto);
    }

    /**
     * {@code DELETE  /role/:id} : delete the "id" role.
     *
     * @param id the id of the roleDto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        log.debug("REST request to delete role : {}", id);
        roleService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
