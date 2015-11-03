package com.example.WeatherForecast.common;

/**
 * Created by James on 15/10/19.
 */
public class City {
    private String province;
    private String city;
    private String number;
    private String allpy;
    private String allfistpy;
    private String firstpy;

    public City(String province, String city, String number, String allpy, String allfistpy, String firstpy) {
        this.province = province;
        this.city = city;
        this.number = number;
        this.allpy = allpy;
        this.allfistpy = allfistpy;
        this.firstpy = firstpy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAllpy() {
        return allpy;
    }

    public void setAllpy(String allpy) {
        this.allpy = allpy;
    }

    public String getAllfistpy() {
        return allfistpy;
    }

    public void setAllfistpy(String allfistpy) {
        this.allfistpy = allfistpy;
    }

    public String getFirstpy() {
        return firstpy;
    }

    public void setFirstpy(String firstpy) {
        this.firstpy = firstpy;
    }
}
