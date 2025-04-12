package com.example.quicknews.models;

public class Country {
    private String name;
    private String code;
    private String flagUrl;

    public Country(String name, String code, String flagUrl) {
        this.name = name;
        this.code = code;
        this.flagUrl = flagUrl;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getFlagUrl() {
        return flagUrl;
    }
}
