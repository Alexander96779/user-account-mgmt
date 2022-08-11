package com.z.useraccountmgmt.service.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.configuration.SecurityConfiguration;
import com.z.useraccountmgmt.exceptions.ResourceAlreadyExistsException;
import com.z.useraccountmgmt.exceptions.ResourceNotFoundException;
import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.model.PasswordResetToken;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.PasswordResetRequest;
import com.z.useraccountmgmt.model.request.UserRequest;
import com.z.useraccountmgmt.model.response.UserResponse;
import com.z.useraccountmgmt.repository.UserRepository;
import com.z.useraccountmgmt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    SecurityConfiguration security;

    @Autowired
    Mapper<User, UserDto, UserRequest, UserResponse> userMapper;

    @Override
    public User createUser(String email, String password){
        userRepository.findByEmail(email)
        .ifPresent(u -> new ResourceAlreadyExistsException("Email exists, please login. If you forgot your password reset it"));
        User user = new User();
        user.setEmail(email);
        user.setPassword(security.getPasswordEncoder().encode(password));
        User userCreated = userRepository.save(user);
        return userCreated;
    }

    @Override
    public List<UserDto> getUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        

        for (User user : users) {
            userDtos.add(userMapper.mapEntityToDto(user));
        }

        return userDtos;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.mapEntityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.mapEntityToDto(user);
    }

    @Override
    public UserDto changePassword(PasswordResetToken passwordResetToken, PasswordResetRequest passwordResetRequest) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (!passwordResetRequest.getPassword().equals(passwordResetRequest.getConfirmPassword())) {
            throw new ValidationException("Password don't match");
        }
        if (passwordResetToken.getExpiryDate().before(currentTimestamp)) {
            throw new ValidationException("Token is expired");
        }
        User user = passwordResetToken.getUser();
        user.setPassword(security.getPasswordEncoder().encode(passwordResetRequest.getPassword()));
        User updatedUser = userRepository.save(user);
        return userMapper.mapEntityToDto(updatedUser);
    }
}
