package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profile")
public class RecruiterProfile {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    @MapsId
    private Users userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private Integer userAccountId;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;
    @Column(name = "company")
    private String company;

    @Column(nullable = true, length = 64)
    private String profilePhoto;

    //constructors

    public RecruiterProfile() {
    }

    public RecruiterProfile(Users userId, Integer userAccountId, String firstName, String lastName,
                            String city, String country, String state, String company, String profilePhoto) {
        this.userId = userId;
        this.userAccountId = userAccountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.state = state;
        this.company = company;
        this.profilePhoto = profilePhoto;
    }

    public RecruiterProfile(Users user) {
        this.userId = user;
    }

    //getters and setters

    public Integer getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Integer userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    @Transient
    public String getPhotosImagePath(){
        if(profilePhoto == null){
            return null;
        }
        return "photos/recruiter/" + userAccountId + "/" + profilePhoto;
    }

    //toString Method

    @Override
    public String toString() {
        return "RecruiterProfile{" +
                "userId=" + userId +
                ", userAccountId=" + userAccountId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", company='" + company + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
