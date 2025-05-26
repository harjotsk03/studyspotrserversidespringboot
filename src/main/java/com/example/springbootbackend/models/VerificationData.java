package com.example.springbootbackend.models;

public class VerificationData {
    private String code;
    private String jwt;
    private long timestamp;

    public VerificationData() {
        // Default constructor for frameworks like Spring and Mongo
    }

    public VerificationData(String code, String jwt, long timestamp) {
        this.code = code;
        this.jwt = jwt;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
