package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.LoanInstallmentMovement;
import com.bank.bankproject.service.dto.LoanInstallmentMovementDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link LoanInstallmentMovement} and its DTO {@link LoanInstallmentMovementDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface LoanInstallmentMovementMapper extends EntityMapper<LoanInstallmentMovementDto, LoanInstallmentMovement> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanInstallmentMovementDto toDtoId(LoanInstallmentMovement loanInstallmentMovement);
}