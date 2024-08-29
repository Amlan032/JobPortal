package com.personalproject1.jobportal.repository;

import com.personalproject1.jobportal.entity.JobPostActivity;
import com.personalproject1.jobportal.entity.JobSeekerApply;
import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {
    List<JobSeekerSave> findByUserId(JobSeekerProfile userId);

    List<JobSeekerSave> findByJob(JobPostActivity jobId);
}
