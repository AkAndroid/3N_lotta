package com.a3nlotta.model.home.tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TicketData implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ticket_name")
    @Expose
    private String ticketName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fee")
    @Expose
    private String fee;
    @SerializedName("status")
    @Expose
    private String status;
    private final static long serialVersionUID = 8907067425107301984L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
