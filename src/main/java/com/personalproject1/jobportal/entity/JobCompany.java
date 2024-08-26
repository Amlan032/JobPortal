package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_company")
public class JobCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "logo")
    private String logo;
    @Column(name = "name")
    private String name;

    //constructors

    public JobCompany() {
    }

    public JobCompany(Integer id, String logo, String name) {
        this.id = id;
        this.logo = logo;
        this.name = name;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //toString Method

    @Override
    public String toString() {
        return "JobCompany{" +
                "id=" + id +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
