package com.z.useraccountmgmt.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.z.useraccountmgmt.configuration.JWTConfig;
import com.z.useraccountmgmt.exceptions.AuthException;
import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.AuthenticationRequest;
import com.z.useraccountmgmt.model.request.SignUpRequest;
import com.z.useraccountmgmt.model.request.UserRequest;
import com.z.useraccountmgmt.model.response.ApiResponse;
import com.z.useraccountmgmt.model.response.UserResponse;
import com.z.useraccountmgmt.repository.UserRepository;
import com.z.useraccountmgmt.service.AuthService;
import com.z.useraccountmgmt.service.MyUserDetailsService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    Mapper<User, UserDto, UserRequest, UserResponse> userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService  userDetailsService;

    @Autowired
    private JWTConfig jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserResponse>> signUp(@RequestBody @Valid SignUpRequest signUpRequest,
            Errors result) {
        // Check validation errors
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("::"));
            throw new ValidationException(errors);
        }
        if (!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            throw new ValidationException("Password don't match");
        }

        UserDto userDto = authService.signUpUser(signUpRequest.mapToDto());

        final String message = "User created successfully..";
        ApiResponse<UserResponse> body = new ApiResponse<>(HttpStatus.CREATED, message, userMapper.mapDtoToResponse(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(body);

    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<String>> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest, Authentication authentication,
            HttpSession session) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new AuthException(ex.getMessage());
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        final String message = "Users logged in successfully...";

        ApiResponse<String> body = new ApiResponse<>(HttpStatus.OK, message, jwt);

        return ResponseEntity.ok(body);
    }
}
