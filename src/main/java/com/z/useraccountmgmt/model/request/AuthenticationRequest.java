package com.z.useraccountmgmt.model.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthenticationRequest implements Serializable {
    @NotBlank(message = "Please, Provide your email.")
    private String email;
    @NotBlank(message = "Please, Provide your password.")
    private String password;
}
