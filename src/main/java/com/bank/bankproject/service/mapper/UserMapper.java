package com.bank.bankproject.service.mapper;

import com.bank.bankproject.domain.User;
import com.bank.bankproject.service.dto.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link User} and its DTO {@link com.bank.bankproject.service.dto.UserDto}.
 */
@Mapper(
        componentModel = "spring",
        uses = {}
)

public interface UserMapper extends EntityMapper<UserDto, User> {

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDto toDtoId(User user);
}
