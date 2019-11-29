package com.mabel.android.findtrackerpoint.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Job implements Serializable {

    int id;
    @SerializedName("job-id")
    String jobid;
    String company;
    String address;
    Geolocation geolocation;

    public Job(int id, String jobid, String company, String address, Geolocation geolocation) {
        this.id = id;
        this.jobid = jobid;
        this.company = company;
        this.address = address;
        this.geolocation = geolocation;
    }

    public int getId() {
        return id;
    }

    public String getJobid() {
        return jobid;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public Geolocation getGeolocation() {
        return geolocation;
    }


}
