package com.z.useraccountmgmt.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.z.useraccountmgmt.validator.PasswordConstraint;

import lombok.Data;

@Data
public class PasswordResetRequest {
    @NotBlank(message = "Please provide your password")
    @Size(min=8, message = "Password should be at least 8 characters")
    @PasswordConstraint
    private String password;
    @NotBlank(message = "Please confirm your password")
    @Size(min=8, message = "Password should be at least 8 characters")
    private String confirmPassword;
}
