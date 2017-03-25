package com.example.portable.hackathon.dtos;

/**
 * Created by Portable on 2017-03-25.
 */

public class Playlist {
    private String idTitle;

    public String getIdTitle() {
        return idTitle;
    }
    public void setIdTitle(String idTitle) {
        this.idTitle = idTitle;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String summary;

    public String getSummary () {
        return summary;
    }
    public void setSummary(String summary){
        this.summary = summary;
    }

}
