package com.example.springbootbackend.security;

public class LogInDTO {
    private String loginId;
    private String password;

    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

