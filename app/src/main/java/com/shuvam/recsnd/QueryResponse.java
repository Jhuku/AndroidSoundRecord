package com.shuvam.recsnd;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class QueryResponse {

    @SerializedName("results")
    ArrayList<Results> res;

    public ArrayList<Results> getRes() {
        return res;
    }

    public void setRes(ArrayList<Results> res) {
        this.res = res;
    }


}
