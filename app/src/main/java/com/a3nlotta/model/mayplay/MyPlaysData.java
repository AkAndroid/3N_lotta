
package com.a3nlotta.model.mayplay;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyPlaysData implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("draw_name")
    @Expose
    private String drawName;
    @SerializedName("fees")
    @Expose
    private String fees;
    @SerializedName("win_price")
    @Expose
    private String winPrice;
    @SerializedName("loss")
    @Expose
    private String loss;
    @SerializedName("rank")
    @Expose
    private String rank;
    @SerializedName("card_name")
    @Expose
    private String cardName;
    @SerializedName("card_number")
    @Expose
    private String cardNumber;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("cvv")
    @Expose
    private String cvv;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private final static long serialVersionUID = -5012007048038846562L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MyPlaysData withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MyPlaysData withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public MyPlaysData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public MyPlaysData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public MyPlaysData withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MyPlaysData withCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public MyPlaysData withState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public MyPlaysData withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public MyPlaysData withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public MyPlaysData withOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getDrawName() {
        return drawName;
    }

    public void setDrawName(String drawName) {
        this.drawName = drawName;
    }

    public MyPlaysData withDrawName(String drawName) {
        this.drawName = drawName;
        return this;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public MyPlaysData withFees(String fees) {
        this.fees = fees;
        return this;
    }

    public String getWinPrice() {
        return winPrice;
    }

    public void setWinPrice(String winPrice) {
        this.winPrice = winPrice;
    }

    public MyPlaysData withWinPrice(String winPrice) {
        this.winPrice = winPrice;
        return this;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public MyPlaysData withLoss(String loss) {
        this.loss = loss;
        return this;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public MyPlaysData withRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public MyPlaysData withCardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public MyPlaysData withCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public MyPlaysData withMonth(String month) {
        this.month = month;
        return this;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public MyPlaysData withYear(String year) {
        this.year = year;
        return this;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public MyPlaysData withCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public MyPlaysData withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public MyPlaysData withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

}
