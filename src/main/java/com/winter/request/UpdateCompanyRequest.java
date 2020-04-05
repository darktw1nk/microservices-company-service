package com.winter.request;

import java.math.BigDecimal;

public class UpdateCompanyRequest {
    private BigDecimal money;
    private Long companyId;

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
