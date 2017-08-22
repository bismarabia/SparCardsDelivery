
package com.bisma.rabia.sparcardsdelivery.model.response.setCard;

import com.bisma.rabia.sparcardsdelivery.model.request.Params;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetCards {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("id")
    @Expose
    private String id;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
