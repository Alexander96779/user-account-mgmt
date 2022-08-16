package com.z.useraccountmgmt.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.model.Verification;
import com.z.useraccountmgmt.model.dto.VerificationDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.VerificationRequest;
import com.z.useraccountmgmt.model.request.VerificationStatusRequest;
import com.z.useraccountmgmt.model.response.ApiResponse;
import com.z.useraccountmgmt.model.response.VerificationResponse;
import com.z.useraccountmgmt.service.VerificationService;

@Controller
@RequestMapping("/api/v1/verification")
public class VerificationController {

    @Autowired
    VerificationService verificationService;

    @Autowired
    Mapper<Verification, VerificationDto, VerificationRequest, VerificationResponse> verificationMapper;

    @PostMapping("")
    public ResponseEntity<ApiResponse<VerificationResponse>> store( @Valid VerificationRequest verificationRequest, Errors result) {
        // Check validation errors
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("::"));
            throw new ValidationException(errors);
        }
        VerificationDto verificationDto = verificationService.addVerification(verificationRequest);

        final String message = "Account verification created successfully..";
        ApiResponse<VerificationResponse> body = new ApiResponse<>(HttpStatus.CREATED, message,
                verificationMapper.mapDtoToResponse(verificationDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping("/{verificationId}")
    public ResponseEntity<ApiResponse<VerificationResponse>> update(@PathVariable("verificationId") Long verificationId, @RequestBody @Valid VerificationStatusRequest verificationStatusRequest,
            Errors result) {
        // Check validation errors
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("::"));
            throw new ValidationException(errors);
        }
        VerificationDto verificationDto = verificationService.changeStatus(verificationId, verificationStatusRequest);

        final String message = "Verification status changed successfully...";
        ApiResponse<VerificationResponse> body = new ApiResponse<>(HttpStatus.CREATED, message, verificationMapper.mapDtoToResponse(verificationDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
