package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.JobPostActivity;
import com.personalproject1.jobportal.entity.JobSeekerApply;
import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.entity.JobSeekerSave;
import com.personalproject1.jobportal.repository.JobSeekerSaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveService {
    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    @Autowired
    public JobSeekerSaveService(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getJobSeekerSaveByUserId(JobSeekerProfile userId){
        return jobSeekerSaveRepository.findByUserId(userId);
    }

    public List<JobSeekerSave> getJobSeekerSaveByJobId(JobPostActivity jobId){
        return jobSeekerSaveRepository.findByJob(jobId);
    }

    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }

    public void delete(JobSeekerSave jobSeekerSave){
        jobSeekerSaveRepository.delete(jobSeekerSave);
    }
}
