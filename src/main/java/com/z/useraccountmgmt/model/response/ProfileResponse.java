package com.z.useraccountmgmt.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class ProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private int age;
    private String maritalStatus;
    private String photoUrl;
    private String nationality;
    private String verification_status;
}
