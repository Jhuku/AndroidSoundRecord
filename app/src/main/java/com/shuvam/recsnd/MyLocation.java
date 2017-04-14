package com.shuvam.recsnd;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class MyLocation {


    @SerializedName("lat")
    Long latitude;
    @SerializedName("lng")
    Long longitude;

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }
}
