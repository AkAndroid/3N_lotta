
package com.a3nlotta.model.mayplay;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyPlaysModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<MyPlaysData> data = null;
    private final static long serialVersionUID = -3274516839073397229L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public MyPlaysModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MyPlaysModel withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<MyPlaysData> getData() {
        return data;
    }

    public void setData(List<MyPlaysData> data) {
        this.data = data;
    }

    public MyPlaysModel withData(List<MyPlaysData> data) {
        this.data = data;
        return this;
    }

}
