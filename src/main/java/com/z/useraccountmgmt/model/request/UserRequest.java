package com.z.useraccountmgmt.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.z.useraccountmgmt.validator.PasswordConstraint;

import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email")
    @NotNull(message = "Please provide your email")
    private String email;
    @NotBlank(message = "Please provide your password")
    @Size(min=8, message = "Password should be at least 8 characters")
    @PasswordConstraint
    private String password;
    @NotBlank(message = "Please provide roles")
    private String roles;
}
