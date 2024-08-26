package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.repository.JobSeekerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileService {
    private final JobSeekerProfileRepository jobSeekerProfileRepository;

    @Autowired
    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
    }

    public Optional<JobSeekerProfile> findJobSeekerProfileById(Integer id){
        return jobSeekerProfileRepository.findById(id);
    }
}
