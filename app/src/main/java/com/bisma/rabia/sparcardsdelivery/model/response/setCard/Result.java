package com.bisma.rabia.sparcardsdelivery.model.response.setCard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("duplicated:")
    @Expose
    private Duplicated duplicated;
    @SerializedName("status")
    @Expose
    private Integer status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Duplicated getDuplicated() {
        return duplicated;
    }

    public void setDuplicated(Duplicated duplicated) {
        this.duplicated = duplicated;
    }
}
