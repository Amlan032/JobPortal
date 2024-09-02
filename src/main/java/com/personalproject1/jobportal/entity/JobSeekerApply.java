package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "job_seeker_apply",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"userId", "job"})
        })
public class JobSeekerApply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "apply_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date applyDate;

    @Column(name = "cover_letter")
    private String coverLetter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "job", referencedColumnName = "job_post_id")
    private JobPostActivity job;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    //constructors

    public JobSeekerApply() {
    }

    public JobSeekerApply(Integer id, Date applyDate, String coverLetter, JobPostActivity job, JobSeekerProfile userId) {
        this.id = id;
        this.applyDate = applyDate;
        this.coverLetter = coverLetter;
        this.job = job;
        this.userId = userId;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public JobPostActivity getJob() {
        return job;
    }

    public void setJob(JobPostActivity job) {
        this.job = job;
    }

    public JobSeekerProfile getUserId() {
        return userId;
    }

    public void setUserId(JobSeekerProfile userId) {
        this.userId = userId;
    }

    //toString Method

    @Override
    public String toString() {
        return "JobSeekerApply{" +
                "id=" + id +
                ", applyDate=" + applyDate +
                ", coverLetter='" + coverLetter + '\'' +
                ", jobId=" + job +
                ", userId=" + userId +
                '}';
    }
}
