package com.z.useraccountmgmt.service;

import com.z.useraccountmgmt.model.PasswordResetToken;
import com.z.useraccountmgmt.model.User;

public interface PasswordResetTokenService {
    public PasswordResetToken findByToken(String token);

    public PasswordResetToken findByUser(User user);

    public PasswordResetToken storeToken(PasswordResetToken PasswordResetToken);
}
