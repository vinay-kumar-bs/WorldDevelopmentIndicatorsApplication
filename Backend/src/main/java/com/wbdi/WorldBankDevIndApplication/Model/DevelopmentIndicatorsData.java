package com.wbdi.WorldBankDevIndApplication.Model;


import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotBlank;


@Entity
@Table(name = "development_indicators")
@IdClass(DataId.class)
public class DevelopmentIndicatorsData {

    @Id
    @NotBlank(message = "It is required")
    @Column(name = "country_name")
    private String countryName;

    private String countryCode;
    private String region;
    private String incomeGroup;

    @Id
    @Column(name = "`year`")
    private int year;

    private double birthRate;
    private double deathRate;
    private double electricPowerConsumption;
    private long gdp;
    private double gdpPerCapita;
    private double internetUsagePercent;
    private double mortalityRate;
    private double lifeExpectancy;
    private double populationDensity;
    private double unemploymentPercent;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIncomeGroup() {
        return incomeGroup;
    }

    public void setIncomeGroup(String incomeGroup) {
        this.incomeGroup = incomeGroup;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getBirthRate() {
        return birthRate;
    }

    public void setBirthRate(double birthRate) {
        this.birthRate = birthRate;
    }

    public double getDeathRate() {
        return deathRate;
    }

    public void setDeathRate(double deathRate) {
        this.deathRate = deathRate;
    }

    public double getElectricPowerConsumption() {
        return electricPowerConsumption;
    }

    public void setElectricPowerConsumption(double electricPowerConsumption) {
        this.electricPowerConsumption = electricPowerConsumption;
    }

    public long getGdp() {
        return gdp;
    }

    public void setGdp(long gdp) {
        this.gdp = gdp;
    }

    public double getGdpPerCapita() {
        return gdpPerCapita;
    }

    public void setGdpPerCapita(double gdpPerCapita) {
        this.gdpPerCapita = gdpPerCapita;
    }

    public double getInternetUsagePercent() {
        return internetUsagePercent;
    }

    public void setInternetUsagePercent(double internetUsagePercent) {
        this.internetUsagePercent = internetUsagePercent;
    }

    public double getMortalityRate() {
        return mortalityRate;
    }

    public void setMortalityRate(double mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public double getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public double getPopulationDensity() {
        return populationDensity;
    }

    public void setPopulationDensity(double populationDensity) {
        this.populationDensity = populationDensity;
    }

    public double getUnemploymentPercent() {
        return unemploymentPercent;
    }

    public void setUnemploymentPercent(double unemploymentPercent) {
        this.unemploymentPercent = unemploymentPercent;
    }
}
