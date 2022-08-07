package com.a3nlotta.model.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Country implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sortname")
    @Expose
    private String sortname;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phonecode")
    @Expose
    private Integer phonecode;
    private final static long serialVersionUID = -1394388221231717951L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Country withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public Country withSortname(String sortname) {
        this.sortname = sortname;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(Integer phonecode) {
        this.phonecode = phonecode;
    }

    public Country withPhonecode(Integer phonecode) {
        this.phonecode = phonecode;
        return this;
    }

}