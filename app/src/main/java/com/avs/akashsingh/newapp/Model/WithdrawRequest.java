package com.avs.akashsingh.newapp.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {
    private String userId;
    private String mobileNumber;
    private String requestedBy;

    private long coins;

    public WithdrawRequest() {
    }

    public WithdrawRequest(String userId, String mobileNumber, String requestedBy, long coins) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.requestedBy = requestedBy;
        this .coins = coins;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return mobileNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.mobileNumber = emailAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    @ServerTimestamp
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }
}
