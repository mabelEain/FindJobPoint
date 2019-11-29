package com.mabel.android.findtrackerpoint.model;

public class NicePlace {

    private String title;
    private String imageurl;

    public NicePlace(String imageurl, String title) {
        this.title = title;
        this.imageurl = imageurl;
    }

    public NicePlace() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
