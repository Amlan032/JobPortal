package com.personalproject1.jobportal.repository;

import com.personalproject1.jobportal.entity.JobPostActivity;
import com.personalproject1.jobportal.entity.JobSeekerApply;
import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {
    List<JobSeekerSave> findByUserId(JobSeekerProfile userId);

    List<JobSeekerSave> findByJob(JobPostActivity jobId);

    @Query(value = "SELECT * FROM job_seeker_save WHERE job = :jobPostId AND user_id = :userAccountId", nativeQuery = true)
    JobSeekerSave findByUserAndJob(@Param("jobPostId") Integer jobPostId, @Param("userAccountId") Integer userAccountId);
}
