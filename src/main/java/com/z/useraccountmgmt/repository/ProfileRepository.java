package com.z.useraccountmgmt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.z.useraccountmgmt.model.Profile;

public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {
    
}
