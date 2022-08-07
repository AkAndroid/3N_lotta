package com.a3nlotta.model;

import android.widget.Switch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileModel implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("company_id")
    @Expose
    private Integer companyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("profile_img")
    @Expose
    private String profileImg;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("wallet")
    @Expose
    private String wallet;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("mail_notify")
    @Expose
    private Object mailNotify;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    private final static long serialVersionUID = 2781760810392051680L;

    @SerializedName("lang")
    @Expose
    private Object lang;
    @SerializedName("sounds")
    @Expose
    private String sounds;
    @SerializedName("vibration")
    @Expose
    private String vibration;
    @SerializedName("push")
    @Expose
    private String push;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("currency_type")
    @Expose
    private String currencyType;

    @SerializedName("email_notifications")
    @Expose
    private String emailNotifications;

    public Object getLang() {
        return lang;
    }

    public void setLang(Object lang) {
        this.lang = lang;
    }

    public String getSounds() {
        return sounds;
    }

    public void setSounds(String sounds) {
        this.sounds = sounds;
    }

    public String getVibration() {
        return vibration;
    }

    public void setVibration(String vibration) {
        this.vibration = vibration;
    }

    public String getPush() {
        return push;
    }

    public void setPush(String push) {
        this.push = push;
    }

    public String getEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(String emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProfileModel withId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public ProfileModel withCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProfileModel withName(String name) {
        this.name = name;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProfileModel withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public ProfileModel withContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ProfileModel withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ProfileModel withCountry(String country) {
        this.country = country;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ProfileModel withState(String state) {
        this.state = state;
        return this;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ProfileModel withCity(String city) {
        this.city = city;
        return this;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public ProfileModel withProfileImg(String profileImg) {
        this.profileImg = profileImg;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ProfileModel withType(String type) {
        this.type = type;
        return this;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public ProfileModel withWallet(String wallet) {
        this.wallet = wallet;
        return this;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public ProfileModel withActive(String active) {
        this.active = active;
        return this;
    }

    public Object getMailNotify() {
        return mailNotify;
    }

    public void setMailNotify(Object mailNotify) {
        this.mailNotify = mailNotify;
    }

    public ProfileModel withMailNotify(Object mailNotify) {
        this.mailNotify = mailNotify;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProfileModel withEmail(String email) {
        this.email = email;
        return this;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public ProfileModel withEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ProfileModel withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ProfileModel withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public ProfileModel withDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }
}
