package com.bank.bankproject.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class UserDto implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
