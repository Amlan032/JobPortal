package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "job_seeker_save",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"userId", "job"})
        })
public class JobSeekerSave implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "job", referencedColumnName = "job_post_id")
    private JobPostActivity job;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "user_account_id")
    private JobSeekerProfile userId;

    //constructors

    public JobSeekerSave() {
    }

    public JobSeekerSave(Integer id, JobPostActivity job, JobSeekerProfile userId) {
        this.id = id;
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
        return "JobSeekerSave{" +
                "id=" + id +
                ", jobId=" + job +
                ", userId=" + userId +
                '}';
    }
}
