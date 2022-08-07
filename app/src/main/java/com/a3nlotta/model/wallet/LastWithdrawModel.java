
package com.a3nlotta.model.wallet;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LastWithdrawModel implements Serializable
{

    @SerializedName("withdraw_amount")
    @Expose
    private String withdrawAmount;
    private final static long serialVersionUID = 4502080247410734505L;

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public LastWithdrawModel withWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
        return this;
    }

}
