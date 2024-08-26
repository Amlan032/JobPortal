package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users {

    //Field Definitions
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "is_active")
    private boolean isActive;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "registration_date")
    private Date registrationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id")
    private UsersType userTypeId;

    //Constructors

    public Users() {
    }

    public Users(Integer userId, String email, boolean isActive, String password, Date registrationDate, UsersType userTypeId) {
        this.userId = userId;
        this.email = email;
        this.isActive = isActive;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userTypeId = userTypeId;
    }

    //Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UsersType getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UsersType userTypeId) {
        this.userTypeId = userTypeId;
    }

    //toString Method

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", emailId='" + email + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", userTypeId=" + userTypeId +
                '}';
    }
}
