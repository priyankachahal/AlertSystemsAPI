package org.priyanka.cmpe220.response;

import org.priyanka.cmpe220.dataobj.UserProfileDo;

import java.util.List;

public class GetUsersResponse {

    private List<UserProfileDo> users;

    public List<UserProfileDo> getUsers() {
        return users;
    }

    public void setUsers(List<UserProfileDo> users) {
        this.users = users;
    }


}
