package com.winter.request;

public class RegisterCompanyRequest {
    private Long userId;
    private String companyName;

    public RegisterCompanyRequest(Long userId, String companyName) {
        this.userId = userId;
        this.companyName = companyName;
    }

    public RegisterCompanyRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
