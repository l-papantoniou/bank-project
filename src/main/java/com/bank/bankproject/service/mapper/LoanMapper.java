package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.Loan;
import com.bank.bankproject.service.dto.LoanDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Loan} and its DTO {@link LoanDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface LoanMapper extends EntityMapper<LoanDto, Loan> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanDto toDtoId(Loan loan);
}