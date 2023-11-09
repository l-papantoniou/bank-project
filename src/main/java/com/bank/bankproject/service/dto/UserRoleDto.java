package com.bank.bankproject.service.dto;

import com.bank.bankproject.domain.UserRoleId;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleDto implements Serializable {

    private UserRoleId userRoleId;

    private UserDto user;

    private RoleDto role;

}
