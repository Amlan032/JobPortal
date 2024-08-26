package com.personalproject1.jobportal.controller;

import ch.qos.logback.core.util.StringUtil;
import com.personalproject1.jobportal.entity.RecruiterProfile;
import com.personalproject1.jobportal.entity.Users;
import com.personalproject1.jobportal.repository.UsersRepository;
import com.personalproject1.jobportal.service.RecruiterProfileService;
import com.personalproject1.jobportal.util.FileUploadUtil;
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
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository usersRepository;
    private final RecruiterProfileService recruiterProfileService;

    public RecruiterProfileController(UsersRepository usersRepository, RecruiterProfileService recruiterProfileService) {
        this.usersRepository = usersRepository;
        this.recruiterProfileService = recruiterProfileService;
    }

    @GetMapping("/")
    public String showRecruiterProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currUserName = authentication.getName();
            Users user = usersRepository.findByEmail(currUserName).orElseThrow(() -> new UsernameNotFoundException("Could not " +
                    "find user"));
            Optional<RecruiterProfile> recruiterProfile = recruiterProfileService.getTheRecruiter(user.getUserId());
            if(recruiterProfile.isPresent()){
                model.addAttribute("profile", recruiterProfile.get());
            }
        }
        return "recruiter_profile";
    }

    @PostMapping("/addNew")
    public String addNewRecruiter(RecruiterProfile recruiterProfile, @RequestParam("image")MultipartFile image,
                                  Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currUserName = authentication.getName();
            Users user = usersRepository.findByEmail(currUserName).orElseThrow(() -> new UsernameNotFoundException("Could not " +
                    "find user"));
            recruiterProfile.setUserId(user);
            recruiterProfile.setUserAccountId(user.getUserId());
        }
        model.addAttribute("profile", recruiterProfile);
        //add support for image upload
        String imageFileName = "";
        if(!(image.getOriginalFilename().equals(""))){
            imageFileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            recruiterProfile.setProfilePhoto(imageFileName);
        }
        RecruiterProfile savedRecruiterProfile = recruiterProfileService.addNewRecruiter(recruiterProfile);
        String uploadDir = "photos/recruiter/"+savedRecruiterProfile.getUserAccountId();
        try{
            FileUploadUtil.saveFile(uploadDir, imageFileName, image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/dashboard/";
    }
}
