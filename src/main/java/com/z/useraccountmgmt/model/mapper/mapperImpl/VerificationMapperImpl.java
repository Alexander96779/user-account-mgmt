package com.z.useraccountmgmt.model.mapper.mapperImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.z.useraccountmgmt.model.Verification;
import com.z.useraccountmgmt.model.dto.VerificationDto;
import com.z.useraccountmgmt.model.mapper.Mapper;
import com.z.useraccountmgmt.model.request.VerificationRequest;
import com.z.useraccountmgmt.model.response.VerificationResponse;

@Service
@Qualifier("verificationMapperImpl")
public class VerificationMapperImpl implements Mapper<Verification, VerificationDto, VerificationRequest, VerificationResponse> {
    @Override
    public VerificationResponse mapDtoToResponse(VerificationDto d) {
        VerificationResponse verificationResponse = new VerificationResponse();
        BeanUtils.copyProperties(d, verificationResponse);
        return verificationResponse;
    }

    @Override
    public VerificationDto mapRequestToDto(VerificationRequest q) {
        VerificationDto verificationDto = new VerificationDto();
        BeanUtils.copyProperties(q, verificationDto);
        return verificationDto;
    }

    @Override
    public Verification mapDtotoEntity(VerificationDto d) {
        return null;
    }

    @Override
    public VerificationDto mapEntityToDto(Verification e) {
        VerificationDto verificationDto = new VerificationDto();
        BeanUtils.copyProperties(e, verificationDto);
        if(e.getStatus() != null) verificationDto.setStatus(e.getStatus().name());
        return verificationDto;
    }
}
