package com.a3nlotta.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StateResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<State> data = null;
    private final static long serialVersionUID = -3713828370839331751L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public StateResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StateResponse withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<State> getData() {
        return data;
    }

    public void setData(List<State> data) {
        this.data = data;
    }

    public StateResponse withData(List<State> data) {
        this.data = data;
        return this;
    }

    public State getSelectedState(String selectedState) {
        for(State state :getData()){
            if(state.getName().equals(selectedState))
                return state;
        }
        return null;
    }

    public String[] getStateArray() {

        List<String> list=new ArrayList<>();
        list.add("State");
        for(State state :getData()){
            list.add(state.getName());
        }

        return list.toArray(new String[getData().size()]);
    }

    public int getSelectedStatePos(String state) {
        for(int i=0;i<getData().size();i++){
            if(getData().get(i).getName().equals(state))
                return i;
        }
        return 0;
    }
}
