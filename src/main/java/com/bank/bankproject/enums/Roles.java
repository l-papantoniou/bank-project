package com.bank.bankproject.enums;

import lombok.Getter;

@Getter
public enum Roles {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String description;

    Roles(String description) {
        this.description = description;
    }

}
