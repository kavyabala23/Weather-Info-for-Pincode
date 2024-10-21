package com.example.WeatherInfoForPincode.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherDataResponse {
    private int id;
    private String pincode;
  //  private GeoCodingResponse geoCodingResponse;
    private LocalDate forDate;
    private WeatherResponse weatherData;
}
