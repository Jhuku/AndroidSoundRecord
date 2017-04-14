package com.shuvam.recsnd;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class Geometry {

    @SerializedName("location")
    MyLocation loc;

    public MyLocation getLoc() {
        return loc;
    }

    public void setLoc(MyLocation loc) {
        this.loc = loc;
    }
}
