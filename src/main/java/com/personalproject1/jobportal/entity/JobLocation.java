package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "job_location")
public class JobLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;

    //constructors

    public JobLocation() {
    }

    public JobLocation(Integer id, String city, String country, String state) {
        this.id = id;
        this.city = city;
        this.country = country;
        this.state = state;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //toString method

    @Override
    public String toString() {
        return "JobLocation{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
