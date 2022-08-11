package com.z.useraccountmgmt.service;


public interface EmailService {
    public void sendASimpleMail(String to, String subject, String message);
}
