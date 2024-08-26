package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "experience_level")
    private String experienceLevel;
    @Column(name = "name")
    private String name;
    @Column(name = "years_of_experience")
    private String yearsOfExperience;

    @ManyToOne(targetEntity = JobSeekerProfile.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_seeker_profile")
    private JobSeekerProfile jobSeekerProfile;

    //constructors

    public Skills() {
    }

    public Skills(Integer id, String experienceLevel, String name, String yearsOfExperience, JobSeekerProfile jobSeekerProfile) {
        this.id = id;
        this.experienceLevel = experienceLevel;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.jobSeekerProfile = jobSeekerProfile;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public JobSeekerProfile getJobSeekerProfile() {
        return jobSeekerProfile;
    }

    public void setJobSeekerProfile(JobSeekerProfile jobSeekerProfile) {
        this.jobSeekerProfile = jobSeekerProfile;
    }

    //toString Method

    @Override
    public String toString() {
        return "Skills{" +
                "id=" + id +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", name='" + name + '\'' +
                ", yearsOfExperience='" + yearsOfExperience + '\'' +
                ", jobSeekerProfile=" + jobSeekerProfile +
                '}';
    }
}
