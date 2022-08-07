package com.a3nlotta.model.draw;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DrawModel implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("draw_name")
    @Expose
    private String drawName;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("prize")
    @Expose
    private String prize;
    @SerializedName("entry_fee")
    @Expose
    private String entryFee;
    @SerializedName("t_no")
    @Expose
    private String tNo;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("how_to_play")
    @Expose
    private String howToPlay;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("days")
    @Expose
    private String days;
    @SerializedName("updated_date")
    @Expose
    private String updatedDate;
    private final static long serialVersionUID = -5521637573704944044L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DrawModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getDrawName() {
        return drawName;
    }

    public void setDrawName(String drawName) {
        this.drawName = drawName;
    }

    public DrawModel withDrawName(String drawName) {
        this.drawName = drawName;
        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DrawModel withText(String text) {
        this.text = text;
        return this;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public DrawModel withPrize(String prize) {
        this.prize = prize;
        return this;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(String entryFee) {
        this.entryFee = entryFee;
    }

    public DrawModel withEntryFee(String entryFee) {
        this.entryFee = entryFee;
        return this;
    }

    public String gettNo() {
        return tNo;
    }

    public void settNo(String tNo) {
        this.tNo = tNo;
    }

    public DrawModel withtNo(String tNo) {
        this.tNo = tNo;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public DrawModel withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public DrawModel withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DrawModel withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getHowToPlay() {
        return howToPlay;
    }

    public void setHowToPlay(String howToPlay) {
        this.howToPlay = howToPlay;
    }

    public DrawModel withHowToPlay(String howToPlay) {
        this.howToPlay = howToPlay;
        return this;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public DrawModel withCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public DrawModel withUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
