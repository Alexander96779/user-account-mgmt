package com.z.useraccountmgmt.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class VerificationStatusRequest {
    @NotBlank(message = "Please provide verification status")
    private String status;
}
