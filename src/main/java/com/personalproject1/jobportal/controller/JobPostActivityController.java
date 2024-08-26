package com.personalproject1.jobportal.controller;

import com.personalproject1.jobportal.entity.JobPostActivity;
import com.personalproject1.jobportal.entity.RecruiterJobsDto;
import com.personalproject1.jobportal.entity.RecruiterProfile;
import com.personalproject1.jobportal.entity.Users;
import com.personalproject1.jobportal.service.JobPostActivityService;
import com.personalproject1.jobportal.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class JobPostActivityController {
    private final UsersService usersService;
    private final JobPostActivityService jobPostActivityService;

    @Autowired
    public JobPostActivityController(UsersService usersService, JobPostActivityService jobPostActivityService) {
        this.usersService = usersService;
        this.jobPostActivityService = jobPostActivityService;
    }

    @GetMapping("/dashboard/")
    public String searchJobs(Model model){
        Object currentUserProfile = usersService.getCurrentUserProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currUserName = authentication.getName();
            model.addAttribute("username", currUserName);
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                List<RecruiterJobsDto> recruiterJobsDtos = jobPostActivityService.getRecruiterJobs(
                        ((RecruiterProfile) currentUserProfile).getUserAccountId()
                );
                model.addAttribute("jobPost", recruiterJobsDtos);
            }
        }
        model.addAttribute("user", currentUserProfile);
        System.out.println("DashBoard");
        return "dashboard";
    }

    @GetMapping("/dashboard/add")
    public String addJobs(Model model){
        model.addAttribute("jobPostActivity", new JobPostActivity());
        model.addAttribute("user", usersService.getCurrentUserProfile());
        return "add-jobs";
    }

    @PostMapping("/dashboard/addNew")
    public String addNewJob(JobPostActivity jobPostActivity, Model model){
        Users user = usersService.getCurrentUser();
        if(user != null){
            jobPostActivity.setPostedById(user);
        }
        jobPostActivity.setPostedDate(new Date(System.currentTimeMillis()));
        model.addAttribute("jobPostActivity", jobPostActivity);
        jobPostActivityService.addNew(jobPostActivity);
        return "redirect:/dashboard/";
    }
}
