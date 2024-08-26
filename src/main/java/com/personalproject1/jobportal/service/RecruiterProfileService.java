package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.RecruiterProfile;
import com.personalproject1.jobportal.repository.RecruiterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getTheRecruiter(Integer id){
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile addNewRecruiter(RecruiterProfile recruiterProfile) {
        RecruiterProfile savedRecruiterProfile = recruiterProfileRepository.save(recruiterProfile);
        return savedRecruiterProfile;
    }
}
