package com.shuvam.recsnd;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class Results {

    @SerializedName("vicinity")
    String vicinity;

    public String getVicinity() {
        return vicinity;
    }

    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    @SerializedName("geometry")
    Geometry geo;

    public Geometry getGeo() {
        return geo;
    }

    public void setGeo(Geometry geo) {
        this.geo = geo;
    }
}
