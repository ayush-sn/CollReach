package com.collreach.userprofile.model.response;

import java.util.LinkedHashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class UserProfileSkillsResponse {
    String profileAccessKey;
    String username;
    LinkedHashMap<String, Integer> skillsUpvote;

    public UserProfileSkillsResponse() {
        this.skillsUpvote = new LinkedHashMap<>();
    }

    public UserProfileSkillsResponse(String profileAccessKey, String username) {
        this.profileAccessKey = profileAccessKey;
        this.username = username;
        this.skillsUpvote = new LinkedHashMap<>();
    }

    public String getProfileAccessKey() {
        return profileAccessKey;
    }

    public void setProfileAccessKey(String profileAccessKey) {
        this.profileAccessKey = profileAccessKey;
    }

    public LinkedHashMap<String, Integer> getSkillsUpvote() {
        return skillsUpvote;
    }

    public void setSkillsUpvote(LinkedHashMap<String, Integer> skillsUpvote) {
        this.skillsUpvote = skillsUpvote;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
