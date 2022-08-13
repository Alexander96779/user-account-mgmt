package com.z.useraccountmgmt.service;

import com.z.useraccountmgmt.model.dto.ProfileDto;
import com.z.useraccountmgmt.model.request.ProfileRequest;

public interface ProfileService {
   ProfileDto addProfile(ProfileRequest profileRequest); 
}
