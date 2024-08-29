package com.personalproject1.jobportal.controller;

import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.entity.Skills;
import com.personalproject1.jobportal.entity.Users;
import com.personalproject1.jobportal.service.JobSeekerProfileService;
import com.personalproject1.jobportal.service.UsersService;
import com.personalproject1.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {
    private final JobSeekerProfileService jobSeekerProfileService;
    private final UsersService usersService;

    @Autowired
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersService usersService) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersService = usersService;
    }

    @GetMapping("/")
    public String JobSeekerProfile(Model model){
        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills = new ArrayList<>();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = usersService.getUserByEmail(authentication.getName()).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.findJobSeekerProfileById(user.getUserId());
            if(seekerProfile.isPresent()){
                jobSeekerProfile = seekerProfile.get();
                if(jobSeekerProfile.getSkills().isEmpty()){
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }
            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }
        return "job-seeker-profile";
    }

    @PostMapping("/addNew")
    public String addNewJobSeeker(JobSeekerProfile jobSeekerProfile,
                                  @RequestParam("image")MultipartFile image,
                                  @RequestParam("pdf")MultipartFile resume,
                                  Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currUserName = authentication.getName();
            Users user = usersService.getUserByEmail(currUserName).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
            );
            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());
        }
        List<Skills> skillsList = new ArrayList<>();
        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skillsList);

        //associate skills and jobSeekerProfile
        for(Skills skill : jobSeekerProfile.getSkills()){
            skill.setJobSeekerProfile(jobSeekerProfile);
        }

        //add support for image and resume
        String imageName = "";
        String resumeName = "";
        if(!(Objects.equals(image.getOriginalFilename(), ""))){
            imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }

        if(!(Objects.equals(resume.getOriginalFilename(), ""))){
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(resume.getOriginalFilename()));
            jobSeekerProfile.setResume(resumeName);
        }

        JobSeekerProfile savedJobSeekerProfile = jobSeekerProfileService.addNewJobSeekerProfile(jobSeekerProfile);
        try{
            String uploadDir = "photos/candidate/"+ jobSeekerProfile.getUserAccountId();
            if(!(Objects.equals(image.getOriginalFilename(), ""))){
                FileUploadUtil.saveFile(uploadDir, imageName, image);
            }

            if(!(Objects.equals(resume.getOriginalFilename(), ""))){
                FileUploadUtil.saveFile(uploadDir, resumeName, resume);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/dashboard/";
    }
}
