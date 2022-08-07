
package com.a3nlotta.model.home;

import java.io.Serializable;
import java.util.List;

import com.a3nlotta.model.draw.DrawModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("banners")
    @Expose
    private List<BannerModel> banners = null;
    @SerializedName("draw")
    @Expose
    private List<DrawModel> draw = null;
    @SerializedName("winners")
    @Expose
    private List<WinnerModel> winners = null;
    private final static long serialVersionUID = 89591941034154554L;

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

    public List<BannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerModel> banners) {
        this.banners = banners;
    }

    public List<WinnerModel> getWinners() {
        return winners;
    }

    public void setWinners(List<WinnerModel> winners) {
        this.winners = winners;
    }

    public List<DrawModel> getDraw() {
        return draw;
    }

    public void setDraw(List<DrawModel> draw) {
        this.draw = draw;
    }
}
