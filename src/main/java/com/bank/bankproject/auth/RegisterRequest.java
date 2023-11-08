package com.bank.bankproject.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RegisterRequest {

    private String firstname;

    private String lastname;

    private String email;

    private String password;
}
