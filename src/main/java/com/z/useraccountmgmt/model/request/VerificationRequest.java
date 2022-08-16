package com.z.useraccountmgmt.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class VerificationRequest {
    @NotBlank(message = "Please provide your nid or passport number.")
    private String nidPassport;
    @NotNull(message = "Please provide image or pdf document to upload")
    private MultipartFile file;
}

