package org.priyanka.cmpe220.request;

import org.priyanka.cmpe220.dataobj.UserProfileDo;

public class CreateUserProfileRequest {

    private UserProfileDo userProfile;

    public UserProfileDo getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDo userProfile) {
        this.userProfile = userProfile;
    }

}