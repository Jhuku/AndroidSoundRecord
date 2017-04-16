package com.shuvam.recsnd;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class UploadResponse {

    String status;
    String code;
    int isParkinsons;
    String UPDRS;

    public String getUPDRS() {
        return UPDRS;
    }

    public void setUPDRS(String UPDRS) {
        this.UPDRS = UPDRS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsParkinsons() {
        return isParkinsons;
    }

    public void setIsParkinsons(int isParkinsons) {
        this.isParkinsons = isParkinsons;
    }
}
