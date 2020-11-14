package com.collreach.userprofile.service.impl;

import com.collreach.userprofile.model.bo.CourseInfo;
import com.collreach.userprofile.model.bo.UserLogin;
import com.collreach.userprofile.model.bo.UserPersonalInfo;
import com.collreach.userprofile.model.repositories.UserLoginRepository;
import com.collreach.userprofile.model.repositories.UserPersonalInfoRepository;
import com.collreach.userprofile.model.repositories.UserSkillsRepository;
import com.collreach.userprofile.model.request.UserSignupRequest;
import com.collreach.userprofile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    UserPersonalInfoRepository userPersonalInfoRepository;
    
    @Autowired
    UserSkillsRepository userSkillsRepository;

    @Override
    public String signup(UserSignupRequest userSignupRequest) {
        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        UserLogin userLogin = new UserLogin();
        String defaultProfilePhotoAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";

        if(userSignupRequest.getEmail() != null) {
            courseInfo.setCourseId(userSignupRequest.getCourseId());
            userPersonalInfo.setCourseInfo(courseInfo);
            userPersonalInfo.setEmail(userSignupRequest.getEmail());
            userPersonalInfo.setName(userSignupRequest.getName());
            userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail());
            userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
            userPersonalInfo.setLinkedinLink(userSignupRequest.getLinkedinLink());
            userPersonalInfo.setDescription(userSignupRequest.getDescription());
            userPersonalInfo.setUserProfilePhoto(defaultProfilePhotoAddress);
            userLogin.setUserPersonalInfo(userPersonalInfo);
        }
        userLogin.setPassword(userSignupRequest.getPassword());
        userLogin.setUserName(userSignupRequest.getUserName());

        userLoginRepository.save(userLogin);
        return "User Signed up successfully.";
    }


    @Override
    public String updateUserPersonalInfo(UserSignupRequest userSignupRequest){
        CourseInfo courseInfo = new CourseInfo();
        UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
        String defaultProfilePhotoAddress = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\images\\default.jpeg";

        courseInfo.setCourseId(userSignupRequest.getCourseId());
        userPersonalInfo.setCourseInfo(courseInfo);
        userPersonalInfo.setEmail(userSignupRequest.getEmail());
        userPersonalInfo.setName(userSignupRequest.getName());
        userPersonalInfo.setAlternateEmail(userSignupRequest.getAlternateEmail());
        userPersonalInfo.setPhoneNo(userSignupRequest.getPhoneNo());
        userPersonalInfo.setLinkedinLink(userSignupRequest.getLinkedinLink());
        userPersonalInfo.setDescription(userSignupRequest.getDescription());
        userPersonalInfo.setUserProfilePhoto(defaultProfilePhotoAddress);
        //userLogin.setUserPersonalInfo(userPersonalInfo);
        String userName = userSignupRequest.getUserName();
        var userLogin = userLoginRepository.findById(userName);
        if(userLogin.isPresent()){
            userPersonalInfoRepository.save(userPersonalInfo);
            userLoginRepository.updateUserPersonalInfo(userName,userPersonalInfo);
            return "Profile updated Successfully.";
        }
       return "Some error occurred while updating user info.";
    }

    @Override
    public String deleteUser(String userName){
        Optional<UserLogin> userLogin = userLoginRepository.findById(userName);
        if(userLogin.isPresent()) {
            UserPersonalInfo user = userLogin.get().getUserPersonalInfo();
            System.out.println("User id is :" + user.getUserId());
            userSkillsRepository.deleteByUserId(user);
            userLoginRepository.deleteById(userName);
            userPersonalInfoRepository.deleteByUserId(user.getUserId());
            return "deleted Successfully.";
        }
        else
            return "User not found.";
    }

    @Override
    public String checkUsername(UserSignupRequest userSignupRequest){
        try {
            boolean user = userLoginRepository.existsById(userSignupRequest.getUserName());
            if (user)
                return "Username exists already.";
            return "";
        }
        catch(Exception e){
            return "Username required.";
        }
    }

    @Override
    public String checkEmail(UserSignupRequest userSignupRequest){
        Boolean email = userPersonalInfoRepository.existsByEmail(userSignupRequest.getEmail());
        Boolean altEmail = userPersonalInfoRepository.existsByAlternateEmail(userSignupRequest.getEmail());
        if(email || altEmail){
            return "Email already exists.";
        }
        return "";
    }


    @Override
    public String checkPhoneNo(UserSignupRequest userSignupRequest){
        Boolean phone = userPersonalInfoRepository.existsByPhoneNo(userSignupRequest.getPhoneNo());
        if(phone){
            return "Phone No. already exists.";
        }
        return "";
    }

    @Override
    public String checkLinkedinLink(UserSignupRequest userSignupRequest){
        Boolean linkedinLink = userPersonalInfoRepository.existsByLinkedinLink(userSignupRequest.getLinkedinLink());
        if(linkedinLink){
            return "This linkedin link already exists.";
        }
        return "";
    }
}
