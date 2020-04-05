package com.winter.request;

public class WorkerRequest {
    private String type;
    private Long companyId;
    private Long workerId;
    private String office;

    public WorkerRequest() {
    }

    public WorkerRequest(Long companyId, Long workerId, String office) {
        this.companyId = companyId;
        this.workerId = workerId;
        this.office = office;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
