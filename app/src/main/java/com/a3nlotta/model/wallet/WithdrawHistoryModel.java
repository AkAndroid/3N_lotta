package com.a3nlotta.model.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WithdrawHistoryModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<WithdrawModel> data = null;
    private final static long serialVersionUID = -2683063811522549360L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<WithdrawModel> getData() {
        return data;
    }

    public void setData(List<WithdrawModel> data) {
        this.data = data;
    }

}