package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.LoanInstallment;
import com.bank.bankproject.service.dto.LoanInstallmentDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link LoanInstallment} and its DTO {@link LoanInstallmentDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface LoanInstallmentMapper extends EntityMapper<LoanInstallmentDto, LoanInstallment> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanInstallmentDto toDtoId(LoanInstallment loanInstallment);
}