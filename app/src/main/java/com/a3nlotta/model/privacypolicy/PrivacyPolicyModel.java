package com.a3nlotta.model.privacypolicy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PrivacyPolicyModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<PrivacyPolicyData> data = null;
    private final static long serialVersionUID = -8425826109827030167L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PrivacyPolicyModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PrivacyPolicyModel withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<PrivacyPolicyData> getData() {
        return data;
    }

    public void setData(List<PrivacyPolicyData> data) {
        this.data = data;
    }

    public PrivacyPolicyModel withData(List<PrivacyPolicyData> data) {
        this.data = data;
        return this;
    }

}