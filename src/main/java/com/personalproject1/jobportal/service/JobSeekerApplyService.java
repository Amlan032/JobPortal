package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.JobPostActivity;
import com.personalproject1.jobportal.entity.JobSeekerApply;
import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.repository.JobSeekerApplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyService {
    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobSeekerApplyService(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getJobSeekerApplyByUserId(JobSeekerProfile userId){
        return jobSeekerApplyRepository.findByUserId(userId);
    }

    public List<JobSeekerApply> getJobSeekerApplyByJobId(JobPostActivity jobId){
        return jobSeekerApplyRepository.findByJob(jobId);
    }

    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }

    public void delete(JobSeekerApply jobSeekerApply){
        jobSeekerApplyRepository.delete(jobSeekerApply);
    }
}
