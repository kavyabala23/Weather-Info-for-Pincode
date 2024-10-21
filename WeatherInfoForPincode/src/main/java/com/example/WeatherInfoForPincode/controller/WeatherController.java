package com.example.WeatherInfoForPincode.controller;


import com.example.WeatherInfoForPincode.dto.WeatherDataResponse;
import com.example.WeatherInfoForPincode.model.WeatherData;
import com.example.WeatherInfoForPincode.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController

public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping("weatherData")
    public ResponseEntity<WeatherDataResponse> getWeather(@RequestParam String pincode, @RequestParam @DateTimeFormat(iso =DateTimeFormat.ISO.DATE)LocalDate for_date){
        WeatherDataResponse weatherData = service.getWeather(pincode,for_date);
        return ResponseEntity.ok(weatherData);
    }
}
