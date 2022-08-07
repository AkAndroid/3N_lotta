package com.a3nlotta.model.address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CountryResponse implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Country> data = null;
    private final static long serialVersionUID = -4156373707259088307L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CountryResponse withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CountryResponse withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<Country> getData() {
        return data;
    }

    public void setData(List<Country> data) {
        this.data = data;
    }

    public CountryResponse withData(List<Country> data) {
        this.data = data;
        return this;
    }

    public Country getSelectedCountry(String selectedCountry) {
        for(Country country :getData()){
            if(country.getName().equals(selectedCountry))
                return country;
        }
        return null;
    }

    public int getSelectedCountryPos(String selectedCountry) {
        for(int i=0;i<getData().size();i++){
            if(getData().get(i).getName().equals(selectedCountry))
                return i;
        }
        return 0;
    }

    public String[] getCountryArray() {

        List<String> countryList=new ArrayList<>();
        countryList.add("Country");
        for(Country country :getData()){
            countryList.add(country.getName());
        }

        return countryList.toArray(new String[getData().size()]);
    }
}