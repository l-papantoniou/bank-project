package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.UserRole;
import com.bank.bankproject.service.dto.UserRoleDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link UserRole} and its DTO {@link com.bank.bankproject.service.dto.UserRoleDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, RoleMapper.class}
)
public interface UserRoleMapper extends EntityMapper<UserRoleDto, UserRole> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "userRoleId", source = "userRoleId")
    UserRoleDto toDtoId(UserRole userRole);

}
