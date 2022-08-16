package com.z.useraccountmgmt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.UserRequest;
import com.z.useraccountmgmt.model.response.ApiResponse;
import com.z.useraccountmgmt.model.response.UserResponse;
import com.z.useraccountmgmt.service.UserService;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    Mapper<User, UserDto, UserRequest, UserResponse> userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> show(@PathVariable("id") Long id){
        UserDto userDto = userService.getUserById(id);
        UserResponse userResponse = userMapper.mapDtoToResponse(userDto);

        final String message = "User retrieved successfully..";
        ApiResponse<UserResponse> body = new ApiResponse<>(HttpStatus.OK, message,
        userResponse);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
