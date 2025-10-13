package com.example.project;

import com.example.project.dtos.LocationRequest;
import com.example.project.dtos.LocationResponse;
import com.example.project.entities.Location;

public class LocationMapper {

    public static LocationResponse toResponse(Location location) {
        LocationResponse dto = new LocationResponse();
        dto.id = location.getId();
        dto.timezone = location.getTimezone();
        dto.cityName = location.getCityName();
        dto.countryName = location.getCountryName();
        dto.latitude = location.getLatitude();
        dto.longitude = location.getLongitude();
        return dto;
    }

    public static Location toEntity(LocationRequest request) {
        return new Location(request.timezone, request.cityName, request.countryName, request.latitude, request.longitude);
    }
}
