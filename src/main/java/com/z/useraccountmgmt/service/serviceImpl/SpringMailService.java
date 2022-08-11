package com.z.useraccountmgmt.service.serviceImpl;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.z.useraccountmgmt.configuration.AppConfiguration;
import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.service.EmailService;
import com.z.useraccountmgmt.service.PasswordResetTokenService;

@Service
public class SpringMailService implements EmailService{
    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    TemplateEngine engine;

    @Autowired
    PasswordResetTokenService passwordResetTokenService;

    @Override
    public void sendASimpleMail(String to, String subject, String content) {
        Context context = new Context();
        context.setVariable("message", content);

        String process = engine.process("email/defaultEmail", context);

        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setSubject(subject);
            helper.setText(process, true);
            helper.setFrom(appConfiguration.getSenderEmail());
            helper.setTo(to);
        } catch (MessagingException e) {
            throw new ValidationException(e.getMessage());
        }
        emailSender.send(mimeMessage);
    }

    
}
