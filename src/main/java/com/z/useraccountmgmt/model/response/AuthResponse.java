package com.z.useraccountmgmt.model.response;

import lombok.Data;

@Data
public class AuthResponse {
    private Long id;
    private String email;
    private String roles;
}
