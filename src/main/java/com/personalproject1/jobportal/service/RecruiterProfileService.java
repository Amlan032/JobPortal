package com.personalproject1.jobportal.service;

import com.personalproject1.jobportal.entity.RecruiterProfile;
import com.personalproject1.jobportal.entity.Users;
import com.personalproject1.jobportal.repository.RecruiterProfileRepository;
import com.personalproject1.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileService {
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository, UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<RecruiterProfile> getTheRecruiter(Integer id){
        return recruiterProfileRepository.findById(id);
    }

    public RecruiterProfile addNewRecruiter(RecruiterProfile recruiterProfile) {
        RecruiterProfile savedRecruiterProfile = recruiterProfileRepository.save(recruiterProfile);
        return savedRecruiterProfile;
    }

    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String username = authentication.getName();
            Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
            Optional<RecruiterProfile> recruiter = getTheRecruiter(user.getUserId());
            return recruiter.orElse(null);
        }
        else{
            return null;
        }
    }
}
