package com.z.useraccountmgmt.model.response;

import com.z.useraccountmgmt.model.Profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String roles;
    private Profile profile;


}
