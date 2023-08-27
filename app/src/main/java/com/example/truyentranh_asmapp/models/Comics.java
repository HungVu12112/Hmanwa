package com.example.truyentranh_asmapp.models;

import java.sql.Array;

public class Comics {
    private String _id;
    private String name;
    private String nametg;
    private String des;
    private String img;
    private String yearxb;
    private String[] imgnd;

    public Comics(String _id, String name, String nametg, String des, String img, String yearxb, String[] imgnd) {
        this._id = _id;
        this.name = name;
        this.nametg = nametg;
        this.des = des;
        this.img = img;
        this.yearxb = yearxb;
        this.imgnd = imgnd;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNametg() {
        return nametg;
    }

    public void setNametg(String nametg) {
        this.nametg = nametg;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getYearxb() {
        return yearxb;
    }

    public void setYearxb(String yearxb) {
        this.yearxb = yearxb;
    }

    public String[] getImgnd() {
        return imgnd;
    }

    public void setImgnd(String[] imgnd) {
        this.imgnd = imgnd;
    }
}
