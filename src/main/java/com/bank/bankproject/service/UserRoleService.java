package com.bank.bankproject.service;

import com.bank.bankproject.domain.UserRole;
import com.bank.bankproject.domain.UserRoleId;
import com.bank.bankproject.repository.UserRoleRepository;
import com.bank.bankproject.service.dto.UserRoleDto;
import com.bank.bankproject.service.mapper.UserRoleMapper;
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
public class UserRoleService {

    private final Logger log = LoggerFactory.getLogger(UserRoleService.class);

    private final UserRoleRepository userRoleRepository;

    private final UserRoleMapper userRoleMapper;


    @Transactional
    public UserRoleDto save(UserRoleDto userRoleDto) {
        log.debug("Request to save a userRole : {}", userRoleDto);
        UserRole userRole = userRoleMapper.toEntity(userRoleDto);
        userRole = userRoleRepository.save(userRole);
        return userRoleMapper.toDto(userRole);
    }

    @Transactional
    public UserRole save(UserRole userRole) {
        log.debug("Request to save UserRole : {}", userRole);
        userRole = userRoleRepository.save(userRole);
        return userRole;
    }

    @Transactional
    public Optional<UserRoleDto> partialUpdate(UserRoleDto userRoleDto) {
        log.debug("Request to partially update User : {}", userRoleDto);

        return userRoleRepository
                .findById(userRoleDto.getUserRoleId())
                .map(existingUserRole -> {
                    userRoleMapper.partialUpdate(existingUserRole, userRoleDto);

                    return existingUserRole;
                })
                .map(userRoleRepository::save)
                .map(userRoleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<UserRoleDto> findAll(Pageable pageable) {
        log.debug("Request to get all UserRole");
        return userRoleRepository.findAll(pageable).map(userRoleMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<UserRoleDto> findOne(UserRoleId id) {
        log.debug("Request to get UserRole : {}", id);
        return userRoleRepository.findById(id).map(userRoleMapper::toDto);
    }

    @Transactional
    public void delete(UserRoleId id) {
        log.debug("Request to delete UserRole : {}", id);
        userRoleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<UserRole> findById(UserRoleId id) {
        log.debug("Request to get a UserRole by its id : {}", id);
        return userRoleRepository.findById(id);
    }


}
