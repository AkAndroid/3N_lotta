
package com.a3nlotta.model.wallet;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletModel implements Serializable
{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("wallet_balance")
    @Expose
    private List<WalletBalanceModel> walletBalanceModel = null;
    @SerializedName("last_withdraw")
    @Expose
    private List<LastWithdrawModel> lastWithdrawModel = null;
    private final static long serialVersionUID = -8611024695047126890L;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public WalletModel withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WalletModel withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public List<WalletBalanceModel> getWalletBalance() {
        return walletBalanceModel;
    }

    public void setWalletBalance(List<WalletBalanceModel> walletBalanceModel) {
        this.walletBalanceModel = walletBalanceModel;
    }

    public WalletModel withWalletBalance(List<WalletBalanceModel> walletBalanceModel) {
        this.walletBalanceModel = walletBalanceModel;
        return this;
    }

    public List<LastWithdrawModel> getLastWithdraw() {
        return lastWithdrawModel;
    }

    public void setLastWithdraw(List<LastWithdrawModel> lastWithdrawModel) {
        this.lastWithdrawModel = lastWithdrawModel;
    }

    public WalletModel withLastWithdraw(List<LastWithdrawModel> lastWithdrawModel) {
        this.lastWithdrawModel = lastWithdrawModel;
        return this;
    }

}
