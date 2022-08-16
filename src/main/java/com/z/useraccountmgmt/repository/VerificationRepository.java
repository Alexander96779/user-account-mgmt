package com.z.useraccountmgmt.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.z.useraccountmgmt.model.Verification;

@Repository
public interface VerificationRepository extends PagingAndSortingRepository<Verification, Long > {
    
}
