package com.z.useraccountmgmt.service.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.z.useraccountmgmt.configuration.AppConfiguration;
import com.z.useraccountmgmt.exceptions.ValidationException;
import com.z.useraccountmgmt.service.AuthService;
import com.z.useraccountmgmt.service.UploadImageService;

@Service
public class UploadImageServiceImpl implements UploadImageService {
    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    AuthService authService;

    
    @Override
    public String uploadImage(String documentName, MultipartFile imageFile) {
        String ext = "." + FilenameUtils.getExtension(imageFile.getOriginalFilename());

        if(ext.equals(".jpeg") || ext.equals(".png") || ext.equals(".gif") || ext.equals(".jpg")){

        String uploadDirectory = System.getProperty("user.dir") + appConfiguration.getUploadDir();
        StringBuilder fileName = new StringBuilder();
        
        documentName = "profile-"+(authService.getAuth().getEmail()) +"-"+documentName.toLowerCase()+ext;
        
        Path fileNameAndPath = Paths.get(uploadDirectory, documentName);
        fileName.append(imageFile.getOriginalFilename());
        try {
        File dir = new File(uploadDirectory);
        if (!dir.exists())
        dir.mkdirs();
        Files.write(fileNameAndPath, imageFile.getBytes());
        } catch (IOException e) {
        throw new IllegalStateException(e);
        }
        return documentName;
    } else {
    throw new ValidationException("Document must be an image");
    }
}

    @Override
    public String uploadDocument(String documentName, MultipartFile docFile) {
        String ext = "." + FilenameUtils.getExtension(docFile.getOriginalFilename());

        if(ext.equals(".pdf") || ext.equals(".jpeg") || ext.equals(".png") || ext.equals(".gif") || ext.equals(".jpg")){

        String uploadDirectory = System.getProperty("user.dir") + appConfiguration.getUploadDir();
        StringBuilder fileName = new StringBuilder();
        
        documentName = "verification-"+(authService.getAuth().getEmail()) +"-"+documentName.toLowerCase()+ext;
        
        Path fileNameAndPath = Paths.get(uploadDirectory, documentName);
        fileName.append(docFile.getOriginalFilename());
        try {
        File dir = new File(uploadDirectory);
        if (!dir.exists())
        dir.mkdirs();
        Files.write(fileNameAndPath, docFile.getBytes());
        } catch (IOException e) {
        throw new IllegalStateException(e);
        }
        return documentName;
    } else {
    throw new ValidationException("Document must be an image or pdf document");
    }
}

}
