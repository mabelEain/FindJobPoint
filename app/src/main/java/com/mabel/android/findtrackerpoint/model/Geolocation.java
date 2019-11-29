package com.mabel.android.findtrackerpoint.model;

import java.io.Serializable;

public class Geolocation implements Serializable {

   public double latitude;
   public double longitude;

    public Geolocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

