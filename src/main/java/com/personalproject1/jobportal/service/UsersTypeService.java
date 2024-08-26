package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.Users;
import com.personalproject1.jobportal.entity.UsersType;
import com.personalproject1.jobportal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {
    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getUserTypeList(){
        return usersTypeRepository.findAll();
    }
}
