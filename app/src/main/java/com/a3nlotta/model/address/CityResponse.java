package com.a3nlotta.model.address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<City> data = null;
    private final static long serialVersionUID = 6729354998985329603L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CityResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CityResponse withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }

    public CityResponse withData(List<City> data) {
        this.data = data;
        return this;
    }
    public City getSelectedCity(String selectedCity) {
        for(City city :getData()){
            if(city.getName().equals(selectedCity))
                return city;
        }
        return null;
    }

    public String[] getCityArray() {

        List<String> list=new ArrayList<>();
        list.add("City");
        for(City city :getData()){
            list.add(city.getName());
        }

        return list.toArray(new String[getData().size()]);
    }

    public int getSelectedCityPos(String city) {
        for(int i=0;i<getData().size();i++){
            if(getData().get(i).getName().equals(city))
                return i;
        }
        return 0;
    }
}