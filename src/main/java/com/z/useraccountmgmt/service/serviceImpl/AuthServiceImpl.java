package com.z.useraccountmgmt.service.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.configuration.SecurityConfiguration;
import com.z.useraccountmgmt.exceptions.ResourceAlreadyExistsException;
import com.z.useraccountmgmt.exceptions.ResourceNotFoundException;
import com.z.useraccountmgmt.model.MyUserDetails;
import com.z.useraccountmgmt.model.Profile;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.Verification;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.UserRequest;
import com.z.useraccountmgmt.model.response.AuthResponse;
import com.z.useraccountmgmt.model.response.UserResponse;
import com.z.useraccountmgmt.repository.ProfileRepository;
import com.z.useraccountmgmt.repository.UserRepository;
import com.z.useraccountmgmt.repository.VerificationRepository;
import com.z.useraccountmgmt.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityConfiguration security;

    @Autowired
    Mapper<User, UserDto, UserRequest, UserResponse> userMapper;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    VerificationRepository verificationRepository;
    
    @Override
    public UserDto signUpUser(UserDto userDto) {
        if (!(userRepository.findByEmail(userDto.getEmail()).isEmpty())) throw new ResourceAlreadyExistsException("User already exist");
        User user = userMapper.mapDtotoEntity(userDto);
        user.setPassword(security.getPasswordEncoder().encode(userDto.getPassword()));
        User createdUser = userRepository.save(user);
        createdUser.setRoles("ROLE_USER");
        Profile profile = new Profile();
        Profile createdProfile = profileRepository.save(profile);
        Verification verification = new Verification();
        Verification createdVerification = verificationRepository.save(verification);
        createdProfile.setVerification(createdVerification);
        createdUser.setProfile(createdProfile);
        userRepository.save(createdUser);

        return userMapper.mapEntityToDto(userRepository.findById(createdUser.getId()).get());
    }

    @Override
    public User getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        return user;
    }

    @Override
    public AuthResponse mapToResponse(String userEmail) {
        AuthResponse authResponse = new AuthResponse();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User not found ..."));
        BeanUtils.copyProperties(user, authResponse);
        return authResponse;
    }
}
