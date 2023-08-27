package com.example.truyentranh_asmapp.models;

public class Comment {
    private MessComment[] listcomment;

    public Comment(MessComment[] listcomment) {
        this.listcomment = listcomment;
    }

    public Comment() {
    }

    public MessComment[] getListcomment() {
        return listcomment;
    }

    public void setListcomment(MessComment[] listcomment) {
        this.listcomment = listcomment;
    }
}
