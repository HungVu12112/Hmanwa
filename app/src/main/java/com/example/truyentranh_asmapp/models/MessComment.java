package com.example.truyentranh_asmapp.models;

public class MessComment {
    private String _id;
    private String comment_time;
    private String content;
    private User id_use;
    private Comics id_truyen;

    public MessComment(String _id, String comment_time, String content, User id_use, Comics id_truyen) {
        this._id = _id;
        this.comment_time = comment_time;
        this.content = content;
        this.id_use = id_use;
        this.id_truyen = id_truyen;
    }

    public MessComment() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getId_use() {
        return id_use;
    }

    public void setId_use(User id_use) {
        this.id_use = id_use;
    }

    public Comics getId_truyen() {
        return id_truyen;
    }

    public void setId_truyen(Comics id_truyen) {
        this.id_truyen = id_truyen;
    }
}
