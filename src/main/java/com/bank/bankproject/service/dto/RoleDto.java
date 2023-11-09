package com.bank.bankproject.service.dto;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleDto implements Serializable {

    private Long id;

    private String description;


}
