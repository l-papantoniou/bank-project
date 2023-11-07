package com.bank.bankproject.service;


import com.bank.bankproject.domain.Employee;
import com.bank.bankproject.repository.EmployeeRepository;
import com.bank.bankproject.service.dto.EmployeeDto;
import com.bank.bankproject.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
@Transactional
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public EmployeeDto save(EmployeeDto employeeDto) {
        log.debug("Request to save Employee : {}", employeeDto);
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public Optional<EmployeeDto> partialUpdate(EmployeeDto employeeDto) {
        log.debug("Request to partially update Employee : {}", employeeDto);

        return employeeRepository
                .findById(employeeDto.getId())
                .map(existingEmployee -> {
                    employeeMapper.partialUpdate(existingEmployee, employeeDto);

                    return existingEmployee;
                })
                .map(employeeRepository::save)
                .map(employeeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDto> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable).map(employeeMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDto> findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id).map(employeeMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> findById(Long id) {
        log.debug("Request to get a Employee by its id : {}", id);
        return employeeRepository.findById(id);
    }

}
