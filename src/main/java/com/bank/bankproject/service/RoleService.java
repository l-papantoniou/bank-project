package com.bank.bankproject.service;

import com.bank.bankproject.domain.Role;
import com.bank.bankproject.repository.RoleRepository;
import com.bank.bankproject.service.dto.RoleDto;
import com.bank.bankproject.service.mapper.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final Logger log = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Transactional
    public RoleDto save(RoleDto roleDto) {
        log.debug("Request to save Role : {}", roleDto);
        Role role = roleMapper.toEntity(roleDto);
        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    @Transactional
    public Role save(Role role) {
        log.debug("Request to save Role : {}", role);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    public Optional<RoleDto> partialUpdate(RoleDto roleDto) {
        log.debug("Request to partially update User : {}", roleDto);

        return roleRepository
                .findById(roleDto.getId())
                .map(existingRole -> {
                    roleMapper.partialUpdate(existingRole, roleDto);

                    return existingRole;
                })
                .map(roleRepository::save)
                .map(roleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<RoleDto> findAll(Pageable pageable) {
        log.debug("Request to get all Users");
        return roleRepository.findAll(pageable).map(roleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<RoleDto> findOne(Long id) {
        log.debug("Request to get Role : {}", id);
        return roleRepository.findById(id).map(roleMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Role : {}", id);
        roleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Role> findById(Long id) {
        log.debug("Request to get a Role by its id : {}", id);
        return roleRepository.findById(id);
    }
}

