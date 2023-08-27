package com.example.truyentranh_asmapp.models;

import com.google.gson.annotations.SerializedName;

public class MessegerUser {
    @SerializedName("msg")
    private String msg;
    @SerializedName("checkLogin")
    private Boolean checkLogin;
    @SerializedName("objectUser")
    private User[] objectUser;

    public MessegerUser(String msg, Boolean checkLogin, User[] objectUser) {
        this.msg = msg;
        this.checkLogin = checkLogin;
        this.objectUser = objectUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getCheckLogin() {
        return checkLogin;
    }

    public void setCheckLogin(Boolean checkLogin) {
        this.checkLogin = checkLogin;
    }

    public User[] getObjectUser() {
        return objectUser;
    }

    public void setObjectUser(User[] objectUser) {
        this.objectUser = objectUser;
    }
}
