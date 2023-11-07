package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.Employee;
import com.bank.bankproject.service.dto.EmployeeDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDto toDtoId(Employee employee);
}