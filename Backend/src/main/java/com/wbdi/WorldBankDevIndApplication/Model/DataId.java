package com.wbdi.WorldBankDevIndApplication.Model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
public class DataId implements Serializable {

    private String countryName;
    private int year;

    public DataId() {
    }

    public DataId(String countryName, int year) {
        this.countryName = countryName;
        this.year = year;
    }


    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
