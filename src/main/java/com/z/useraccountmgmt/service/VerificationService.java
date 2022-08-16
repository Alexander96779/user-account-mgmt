package com.z.useraccountmgmt.service;

import com.z.useraccountmgmt.model.dto.VerificationDto;
import com.z.useraccountmgmt.model.request.VerificationRequest;
import com.z.useraccountmgmt.model.request.VerificationStatusRequest;

public interface VerificationService {
    VerificationDto addVerification(VerificationRequest verificationRequest);
    VerificationDto changeStatus(Long verificationId, VerificationStatusRequest VerificationStatusRequest);
}
