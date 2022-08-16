package com.z.useraccountmgmt.model.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProfileRequest {
    @NotBlank(message = "Please provide your first name.")
    private String firstName;
    @NotBlank(message = "Please provide your last name.")
    private String lastName;
    @NotNull(message = "Please provide date of birth.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @NotBlank(message = "Please provide gender.")
    private String  gender;
    @NotNull(message = "Please provide your age.")
    private int age;
    @NotBlank(message = "Please provide marital status.")
    private String maritalStatus;
    @NotNull(message = "Please provide image to upload")
    private MultipartFile file;
    @NotBlank(message = "Please provide your nationality.")
    private String nationality;
}
