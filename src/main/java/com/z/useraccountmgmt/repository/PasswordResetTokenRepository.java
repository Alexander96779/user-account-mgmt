package com.z.useraccountmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.z.useraccountmgmt.model.PasswordResetToken;
import com.z.useraccountmgmt.model.User;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
    List<PasswordResetToken> findByUser(User user);
}
