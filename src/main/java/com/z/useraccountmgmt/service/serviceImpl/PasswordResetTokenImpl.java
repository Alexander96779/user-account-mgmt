package com.z.useraccountmgmt.service.serviceImpl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.model.PasswordResetToken;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.repository.PasswordResetTokenRepository;
import com.z.useraccountmgmt.service.PasswordResetTokenService;

@Service
public class PasswordResetTokenImpl implements PasswordResetTokenService {
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public PasswordResetToken findByUser(User user) {
        List<PasswordResetToken> tokens = passwordResetTokenRepository.findByUser(user);
        return tokens.get(tokens.size() - 1);
    }

    @Override
    public PasswordResetToken storeToken(PasswordResetToken PasswordResetToken) {
        PasswordResetToken.setExpiryDate(calculateExpiryDate(24*60));
        return passwordResetTokenRepository.save(PasswordResetToken);
    }

    private Timestamp calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    } 
}
