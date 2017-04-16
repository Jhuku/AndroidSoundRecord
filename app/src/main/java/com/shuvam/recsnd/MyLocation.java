package com.shuvam.recsnd;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class MyLocation {


    @SerializedName("lat")
    Double latitude;
    @SerializedName("lng")
    Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
