package com.z.useraccountmgmt.model.response;

import lombok.Data;

@Data
public class VerificationResponse {
    private Long id;
    private String nidPassport;
    private String documentUrl;
    private String  status;
}
