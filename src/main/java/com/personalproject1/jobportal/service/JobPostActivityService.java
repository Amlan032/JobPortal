package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.*;
import com.personalproject1.jobportal.repository.JobPostActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobPostActivityService {
    private final JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiterId){
        List<IRecruiterJobs> recruiterJobsList = jobPostActivityRepository.getRecruiterJobs(recruiterId);
        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
        //conversion from IRecruiterJobs to RecruiterJobsDto
        for(IRecruiterJobs recruiterJob: recruiterJobsList){
            JobLocation jobLocation = new JobLocation(recruiterJob.getLocationId(), recruiterJob.getCity(),
                    recruiterJob.getCountry(), recruiterJob.getState());
            JobCompany jobCompany = new JobCompany(recruiterJob.getCompanyId(), "", recruiterJob.getName());
            recruiterJobsDtoList.add(new RecruiterJobsDto(recruiterJob.getTotalCandidates(), recruiterJob.getJob_post_id(),
                    recruiterJob.getJob_title(),
                    jobLocation, jobCompany));
        }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getTheJob(int id) {
        return jobPostActivityRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Job not found")
        );
    }

    public List<JobPostActivity> getAll() {
        return jobPostActivityRepository.findAll();
    }

    public List<JobPostActivity> search(String job, String location, List<String> jobType,
                                        List<String> jobRemoteness, LocalDate searchDate) {
        if(Objects.isNull(searchDate)){
            return jobPostActivityRepository.searchWithoutDate(job, location, jobType, jobRemoteness);
        }
        return jobPostActivityRepository.searchWithDate(job, location, jobType, jobRemoteness, searchDate);
    }

    public void deleteTheJob(JobPostActivity jobPostActivity){
        jobPostActivityRepository.delete(jobPostActivity);
    }
}
