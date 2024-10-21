package com.example.WeatherInfoForPincode.model;


import com.example.WeatherInfoForPincode.dto.WeatherDataResponse;
import com.example.WeatherInfoForPincode.dto.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private LocalDate forDate;

   @Column(columnDefinition = "TEXT")
    private String weatherData;
    // Use Jackson ObjectMapper to convert between JSON String and WeatherResponse
    @Transient // This annotation indicates that this field should not be persisted
    private WeatherResponse parsedWeatherData;

    public void parseWeatherData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.parsedWeatherData = objectMapper.readValue(this.weatherData, WeatherResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
}
