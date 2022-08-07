package com.a3nlotta.model.home.tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TicketModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private TicketModelData data;
    private final static long serialVersionUID = -7263016947686775634L;

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

    public TicketModelData getData() {
        return data;
    }

    public void setData(TicketModelData data) {
        this.data = data;
    }
}
