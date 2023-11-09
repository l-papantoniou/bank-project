package com.bank.bankproject.service.mapper;


import com.bank.bankproject.domain.Role;
import com.bank.bankproject.service.dto.RoleDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Role} and its DTO {@link com.bank.bankproject.service.dto.RoleDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)
public interface RoleMapper extends EntityMapper<RoleDto, Role> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoleDto toDtoId(Role role);
}
