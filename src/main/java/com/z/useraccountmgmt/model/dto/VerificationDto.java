package com.z.useraccountmgmt.model.dto;

import lombok.Data;

@Data
public class VerificationDto {
    private Long id;
    private String nidPassport;
    private String documentUrl;
    private String  status;
}
