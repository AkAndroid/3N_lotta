
package com.a3nlotta.model.wallet;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletBalanceModel implements Serializable
{

    @SerializedName("wallet")
    @Expose
    private String wallet;
    private final static long serialVersionUID = -1285189143683628799L;

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public WalletBalanceModel withWallet(String wallet) {
        this.wallet = wallet;
        return this;
    }

}
