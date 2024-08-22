package com.personalproject1.jobportal.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_type")
public class UsersType {
    //Field Definitions
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private Integer userTypeId;

    @Column(name = "user_type_name")
    private String userTypeName;

    @OneToMany(targetEntity = Users.class, mappedBy = "userTypeId", cascade = CascadeType.ALL)
    private List<Users> usersList;

    //Constructors

    public UsersType() {
    }

    public UsersType(Integer userTypeId, String userTypeName, List<Users> usersList) {
        this.userTypeId = userTypeId;
        this.userTypeName = userTypeName;
        this.usersList = usersList;
    }

    //Getters and Setters

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }
    //toString Method

    @Override
    public String toString() {
        return "UsersType{" +
                "userTypeId=" + userTypeId +
                ", userTypeName='" + userTypeName + '\'' +
                '}';
    }
}
