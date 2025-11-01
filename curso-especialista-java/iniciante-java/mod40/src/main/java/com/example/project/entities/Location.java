package com.example.project.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timezone", nullable = false)
    private String timezone;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "country_name", nullable = false)
    private String countryName;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    public Location() {}

    public Location(String timezone, String cityName, String countryName, Double latitude, Double longitude) {
        this.timezone = timezone;
        this.cityName = cityName;
        this.countryName = countryName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", timezone='" + timezone + ", city='" + cityName + ", country='" + countryName + ", latitude=" + latitude + ", longitude=" + longitude + '}';
    }
}
