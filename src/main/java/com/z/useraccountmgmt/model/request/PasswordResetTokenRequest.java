package com.z.useraccountmgmt.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PasswordResetTokenRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    @NotNull(message = "Please provide your email")
    private String email;
}
