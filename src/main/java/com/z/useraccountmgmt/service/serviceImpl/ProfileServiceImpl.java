package com.z.useraccountmgmt.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.exceptions.ResourceNotFoundException;
import com.z.useraccountmgmt.model.Profile;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.Profile.EGENDER;
import com.z.useraccountmgmt.model.Profile.EMARITALSTATUS;
import com.z.useraccountmgmt.model.dto.ProfileDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.ProfileRequest;
import com.z.useraccountmgmt.model.response.ProfileResponse;
import com.z.useraccountmgmt.repository.ProfileRepository;
import com.z.useraccountmgmt.service.AuthService;
import com.z.useraccountmgmt.service.ProfileService;
import com.z.useraccountmgmt.service.UploadImageService;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    AuthService authService;

    @Autowired
    UploadImageService uploadImageService;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    Mapper<Profile, ProfileDto, ProfileRequest, ProfileResponse> profileMapper;

    @Override
    public ProfileDto addProfile(ProfileRequest profileRequest) {
        User user = authService.getAuth();
        Profile profile = profileRepository.findById(user.getProfile().getId()).orElseThrow(() -> new ResourceNotFoundException("No Profile found"));
        String documentName = "profile";
        String fileName = uploadImageService.uploadImage(documentName, profileRequest.getFile());
        profile.setFirstName(profileRequest.getFirstName());
        profile.setLastName(profileRequest.getLastName());
        profile.setDateOfBirth(profileRequest.getDateOfBirth());
        profile.setGender(EGENDER.valueOf(profileRequest.getGender().toUpperCase()));
        profile.setMaritalStatus(EMARITALSTATUS.valueOf(profileRequest.getMaritalStatus().toUpperCase()));
        profile.setPhotoUrl(fileName);
        profile.setAge(profileRequest.getAge());
        profile.setNationality(profileRequest.getNationality());
        Profile createdProfile = profileRepository.save(profile);
        return profileMapper.mapEntityToDto(profileRepository.findById(createdProfile.getId()).get());
    }
}
