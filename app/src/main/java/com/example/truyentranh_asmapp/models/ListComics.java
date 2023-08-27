package com.example.truyentranh_asmapp.models;

import com.google.gson.annotations.SerializedName;

public class ListComics {
    @SerializedName("listComic")
    private Comics[] listComic;

    public ListComics(Comics[] listComic) {
        this.listComic = listComic;
    }

    public ListComics() {
    }

    public Comics[] getListComic() {
        return listComic;
    }

    public void setListComic(Comics[] listComic) {
        this.listComic = listComic;
    }
}
