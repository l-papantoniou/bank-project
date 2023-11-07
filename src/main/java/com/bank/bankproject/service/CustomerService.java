package com.bank.bankproject.service;

import com.bank.bankproject.domain.Customer;
import com.bank.bankproject.repository.CustomerRepository;
import com.bank.bankproject.service.dto.CustomerDto;
import com.bank.bankproject.service.mapper.CustomerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link Customer}.
 */
@Service
@Transactional
public class CustomerService {

    private final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerDto save(CustomerDto customerDto) {
        log.debug("Request to save Customer : {}", customerDto);
        Customer customer = customerMapper.toEntity(customerDto);
        customer = customerRepository.save(customer);
        return customerMapper.toDto(customer);
    }

    @Transactional
    public Optional<CustomerDto> partialUpdate(CustomerDto customerDto) {
        log.debug("Request to partially update Customer : {}", customerDto);

        return customerRepository
                .findById(customerDto.getId())
                .map(existingCustomer -> {
                    customerMapper.partialUpdate(existingCustomer, customerDto);

                    return existingCustomer;
                })
                .map(customerRepository::save)
                .map(customerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<CustomerDto> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customerRepository.findAll(pageable).map(customerMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<CustomerDto> findOne(Long id) {
        log.debug("Request to get Customer : {}", id);
        return customerRepository.findById(id).map(customerMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete Customer : {}", id);
        customerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        log.debug("Request to get a Customer by its id : {}", id);
        return customerRepository.findById(id);
    }

}
