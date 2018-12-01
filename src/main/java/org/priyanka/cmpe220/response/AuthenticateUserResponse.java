package org.priyanka.cmpe220.response;

public class AuthenticateUserResponse {

    private boolean success;

    private String name;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}