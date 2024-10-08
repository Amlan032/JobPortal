package com.personalproject1.jobportal.controller;

import com.personalproject1.jobportal.entity.JobSeekerProfile;
import com.personalproject1.jobportal.entity.Skills;
import com.personalproject1.jobportal.entity.Users;
import com.personalproject1.jobportal.service.JobSeekerProfileService;
import com.personalproject1.jobportal.service.UsersService;
import com.personalproject1.jobportal.util.FileDownloadUtil;
import com.personalproject1.jobportal.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public String getCandidateProfile(@PathVariable("id") int id, Model model){
        Optional<JobSeekerProfile> jobSeekerProfile = jobSeekerProfileService.findJobSeekerProfileById(id);
        model.addAttribute("profile", jobSeekerProfile.get());
        return "job-seeker-profile";
    }

    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName, @RequestParam(value = "userID") String userId){
        FileDownloadUtil fileDownloadUtil = new FileDownloadUtil();
        Resource resource = null;

        try{
            resource = fileDownloadUtil.getFileAsResource("photos/candidate/" + userId, fileName);
        }
        catch (IOException ioException){
            return ResponseEntity.badRequest().build();
        }

        if(resource == null){
            return new ResponseEntity<>("File Not Found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
