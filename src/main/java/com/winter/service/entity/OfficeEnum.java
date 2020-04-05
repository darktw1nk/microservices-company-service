package com.winter.service.entity;

public enum OfficeEnum {
    DESIGNERS("design"),
    DEVELOPMENT("development"),
    MARKETERS("marketing");

    private String name;

    OfficeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
