package com.z.useraccountmgmt.model.mapper.mapperImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.dto.UserDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.UserRequest;
import com.z.useraccountmgmt.model.response.UserResponse;

@Service
public class UserMapperImpl implements Mapper<User, UserDto, UserRequest, UserResponse> {

    @Override
    public UserResponse mapDtoToResponse(UserDto d) {
        UserResponse user = new UserResponse();
        BeanUtils.copyProperties(d, user);
        return user;
    }

    @Override
    public UserDto mapRequestToDto(UserRequest q) {
        UserDto user = new UserDto();
        BeanUtils.copyProperties(q, user);
        return user;
    }

    @Override
    public User mapDtotoEntity(UserDto d) {
        User user = new User();
        BeanUtils.copyProperties(d, user);
        return user;
    }

    @Override
    public UserDto mapEntityToDto(User e) {
        UserDto company = new UserDto();
        BeanUtils.copyProperties(e, company);
        return company;
    }
    
}

