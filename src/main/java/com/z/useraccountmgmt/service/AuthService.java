package com.z.useraccountmgmt.service;

import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.response.AuthResponse;

public interface AuthService {
    User getAuth();
    UserDto signUpUser(UserDto userDto);
    AuthResponse mapToResponse(String userEmail);
}
