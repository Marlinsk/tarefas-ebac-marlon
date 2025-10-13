package com.example.project.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

public class LocationRequest {

    @Schema(example = "America/Sao_Paulo")
    public String timezone;

    @Schema(example = "São Paulo")
    public String cityName;

    @Schema(example = "Brasil")
    public String countryName;

    @Schema(example = "-23.5505")
    public Double latitude;

    @Schema(example = "-46.6333")
    public Double longitude;
}
