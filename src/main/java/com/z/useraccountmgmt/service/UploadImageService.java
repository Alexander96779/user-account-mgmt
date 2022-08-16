package com.z.useraccountmgmt.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {
    String uploadImage(String documentName, MultipartFile imageFile);
    String uploadDocument(String documentName, MultipartFile docFile);
}
