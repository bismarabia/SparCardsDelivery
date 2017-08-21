
package com.bisma.rabia.sparcardsdelivery.model.response.masetCards;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMasterBarCodes {

    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("MasterBarCodes")
    @Expose
    private List<MasterBarCode> masterBarCodes = null;
    @SerializedName("error")
    @Expose
    private String error;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<MasterBarCode> getMasterBarCodes() {
        return masterBarCodes;
    }

    public void setMasterBarCodes(List<MasterBarCode> masterBarCodes) {
        this.masterBarCodes = masterBarCodes;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
