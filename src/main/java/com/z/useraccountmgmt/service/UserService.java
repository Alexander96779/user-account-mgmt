package com.z.useraccountmgmt.service;

import java.util.List;

import com.z.useraccountmgmt.model.PasswordResetToken;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.request.PasswordResetRequest;

public interface UserService{
    User createUser(String email, String password);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    List<UserDto> getUsers();
    UserDto changePassword(PasswordResetToken passwordResetToken, PasswordResetRequest passwordResetRequest);
}
