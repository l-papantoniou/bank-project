package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.Customer;
import com.bank.bankproject.service.dto.CustomerDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDto toDtoId(Customer customer);
}
