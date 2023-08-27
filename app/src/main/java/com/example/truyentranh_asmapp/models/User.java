package com.example.truyentranh_asmapp.models;

public class User {
    private String _id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private boolean isCheck;
    private String msg;

    public User(String _id, String username, String password, String email, String fullname, boolean isCheck, String msg) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.isCheck = isCheck;
        this.msg = msg;
    }

    public User() {

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
