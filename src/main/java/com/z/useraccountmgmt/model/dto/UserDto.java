package com.z.useraccountmgmt.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String roles;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
}
