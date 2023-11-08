package com.bank.bankproject.auth;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AuthenticationRequest {

    private String email;

    private String password;
}
