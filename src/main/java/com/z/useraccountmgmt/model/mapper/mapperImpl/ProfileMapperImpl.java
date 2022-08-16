package com.z.useraccountmgmt.model.mapper.mapperImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.model.Profile;
import com.z.useraccountmgmt.model.dto.ProfileDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.ProfileRequest;
import com.z.useraccountmgmt.model.response.ProfileResponse;

@Service
@Qualifier("profileMapperImpl")
public class ProfileMapperImpl implements Mapper<Profile, ProfileDto, ProfileRequest, ProfileResponse> {
    @Override
    public ProfileResponse mapDtoToResponse(ProfileDto d) {
        ProfileResponse profileResponse = new ProfileResponse();
        BeanUtils.copyProperties(d, profileResponse);
        return profileResponse;
    }

    @Override
    public ProfileDto mapRequestToDto(ProfileRequest q) {
        ProfileDto profileDto = new ProfileDto();
        BeanUtils.copyProperties(q, profileDto);
        return profileDto;
    }

    @Override
    public Profile mapDtotoEntity(ProfileDto d) {
        return null;
    }

    @Override
    public ProfileDto mapEntityToDto(Profile e) {
        ProfileDto profileDto = new ProfileDto();
        BeanUtils.copyProperties(e, profileDto);
        if(e.getGender() != null) profileDto.setGender(e.getGender().name());
        if(e.getMaritalStatus() != null) profileDto.setMaritalStatus(e.getMaritalStatus().name());
        return profileDto;
    }
}
