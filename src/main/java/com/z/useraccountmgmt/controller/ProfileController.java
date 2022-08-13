package com.z.useraccountmgmt.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.model.Profile;
import com.z.useraccountmgmt.model.dto.ProfileDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.ProfileRequest;
import com.z.useraccountmgmt.model.response.ApiResponse;
import com.z.useraccountmgmt.model.response.ProfileResponse;
import com.z.useraccountmgmt.service.ProfileService;

@Controller
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Autowired
    Mapper<Profile, ProfileDto, ProfileRequest, ProfileResponse> profileMapper;

    @Autowired
    ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ProfileResponse>> store( @Valid ProfileRequest profileRequest, Errors result) {
        // Check validation errors
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("::"));
            throw new ValidationException(errors);
        }
        ProfileDto profileDto = profileService.addProfile(profileRequest);

        final String message = "User profile created successfully..";
        ApiResponse<ProfileResponse> body = new ApiResponse<>(HttpStatus.CREATED, message,
                profileMapper.mapDtoToResponse(profileDto));

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
