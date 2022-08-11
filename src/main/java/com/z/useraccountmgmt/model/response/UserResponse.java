package com.z.useraccountmgmt.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String roles;

    public UserResponse(Long id, String email, String roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }


}
