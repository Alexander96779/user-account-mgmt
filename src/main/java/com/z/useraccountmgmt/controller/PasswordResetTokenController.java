package com.z.useraccountmgmt.controller;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.z.useraccountmgmt.configuration.AppConfiguration;
import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.model.PasswordResetToken;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.request.PasswordResetRequest;
import com.z.useraccountmgmt.model.request.PasswordResetTokenRequest;
import com.z.useraccountmgmt.model.response.ApiResponse;
import com.z.useraccountmgmt.repository.UserRepository;
import com.z.useraccountmgmt.service.EmailService;
import com.z.useraccountmgmt.service.PasswordResetTokenService;
import com.z.useraccountmgmt.service.UserService;

@Controller
@RequestMapping("/api/v1")
public class PasswordResetTokenController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    EmailService emailService;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    UserService userService;
    
    @PostMapping("/forgotpassword")
    public ResponseEntity<ApiResponse<Void>> store(
            @RequestBody @Valid PasswordResetTokenRequest passwordResetTokenRequest, Errors result) {
        // Check validation errors
        if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("::"));
            throw new ValidationException(errors);
        }
        Optional<User> user = userRepository.findByEmail(passwordResetTokenRequest.getEmail());
        if (user.isEmpty()) {
            throw new ValidationException("This email is not registered to any account, retry with the right email");
        }
        user.ifPresent(u -> {
            try {
                String email = u.getEmail();
                String token = UUID.randomUUID().toString();
                PasswordResetToken passwordResetToken = new PasswordResetToken();
                passwordResetToken.setToken(token);
                passwordResetToken.setUser(u);
                passwordResetTokenService.storeToken(passwordResetToken);

                String link = appConfiguration.getClientUrl() + "/reset-password?token=" + token;
                emailService.sendASimpleMail(email, "Password Reset",
                "Dear " + "<b>"+ email +"</b>" +", <br />" + "To reset your password click the link below,"
                        + "<br /> " + link
                        + "<br /> " +"Thank you.");
            System.out.println("Email was sent successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        final String message = "Password reset token was sent successfully..";
        ApiResponse<Void> body = new ApiResponse<>(HttpStatus.CREATED, message, null);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PostMapping("/resetpassword")
    public ResponseEntity<ApiResponse<Void>> changePassword(@RequestParam("token") String token,
            @RequestBody @Valid PasswordResetRequest passwordResetRequest, Errors result) {
        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);
        if (passwordResetToken == null) {
            throw new ValidationException("your token is invalid");
        }
         // Check validation errors
         if (result.hasErrors()) {
            String errors = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("::"));
            throw new ValidationException(errors);
        }
        userService.changePassword(passwordResetToken, passwordResetRequest);

        final String message = "Password was reset successfully..";
        ApiResponse<Void> body = new ApiResponse<>(HttpStatus.CREATED, message, null);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
