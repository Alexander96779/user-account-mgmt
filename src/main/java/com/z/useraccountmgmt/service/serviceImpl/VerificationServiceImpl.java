package com.z.useraccountmgmt.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.exceptions.ResourceNotFoundException;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.Verification;
import com.z.useraccountmgmt.model.Verification.EVERIFICATIONSTATUS;
import com.z.useraccountmgmt.model.dto.VerificationDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.VerificationRequest;
import com.z.useraccountmgmt.model.request.VerificationStatusRequest;
import com.z.useraccountmgmt.model.response.VerificationResponse;
import com.z.useraccountmgmt.repository.ProfileRepository;
import com.z.useraccountmgmt.repository.VerificationRepository;
import com.z.useraccountmgmt.service.AuthService;
import com.z.useraccountmgmt.service.UploadImageService;
import com.z.useraccountmgmt.service.VerificationService;

@Service
public class VerificationServiceImpl implements VerificationService {
    @Autowired
    AuthService authService;

    @Autowired
    UploadImageService uploadImageService;

    @Autowired
    VerificationRepository verificationRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    Mapper<Verification, VerificationDto, VerificationRequest, VerificationResponse> verificationMapper;

    @Override
    public VerificationDto addVerification(VerificationRequest verificationRequest) {
        User user = authService.getAuth();
        Verification verification = verificationRepository.findById(user.getProfile().getVerification().getId()).orElseThrow(() -> new ResourceNotFoundException("No verification found"));
        String documentName = "verification";
        String fileName = uploadImageService.uploadDocument(documentName, verificationRequest.getFile());
        verification.setNidPassport(verificationRequest.getNidPassport());
        verification.setDocumentUrl(fileName);
        verification.setStatus(EVERIFICATIONSTATUS.PENDING);
        Verification createdVerification = verificationRepository.save(verification);
        return verificationMapper.mapEntityToDto(verificationRepository.findById(createdVerification.getId()).get());
    }

    @Override
    public VerificationDto changeStatus(Long verificationId, VerificationStatusRequest verificationStatusRequest) {
        Verification verification = verificationRepository.findById(verificationId).orElseThrow(() -> new ResourceNotFoundException("Verification document not found"));
        verification.setStatus(EVERIFICATIONSTATUS.valueOf(verificationStatusRequest.getStatus().toUpperCase()));
        Verification createdVerification = verificationRepository.save(verification);
        return verificationMapper.mapEntityToDto(createdVerification);
    }
}
