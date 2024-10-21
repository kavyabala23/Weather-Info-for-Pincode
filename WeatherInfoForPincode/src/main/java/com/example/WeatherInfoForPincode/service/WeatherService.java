package com.example.WeatherInfoForPincode.service;

import com.example.WeatherInfoForPincode.dto.GeoCodingResponse;
import com.example.WeatherInfoForPincode.dto.WeatherDataResponse;
import com.example.WeatherInfoForPincode.dto.WeatherResponse;
import com.example.WeatherInfoForPincode.model.WeatherData;
import com.example.WeatherInfoForPincode.repo.WeatherRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepo repo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.geocoding.api}")
    private String geocodingApiUrl;

    @Value("${external.weather.api}")
    private String weatherApiUrl;

    @Value("${external.api.key}")
    private String apiKey;

//    public WeatherData getWeather(String pincode, LocalDate forDate) {
//           return repo.findByPincodeAndForDate(pincode,forDate)
//                   .orElseGet(() -> fetchAndSaveWeatherData(pincode ,forDate));
//
//    }

    public WeatherDataResponse getWeather(String pincode, LocalDate forDate) {
        // Find weather data in the repository
        WeatherData weatherData = repo.findByPincodeAndForDate(pincode, forDate)
                .orElseGet(() -> fetchAndSaveWeatherData(pincode, forDate));
        // Map to WeatherDataResponse
        return mapToWeatherDataResponse(weatherData);
    }

    private WeatherDataResponse mapToWeatherDataResponse(WeatherData weatherData) {
        WeatherDataResponse response = new WeatherDataResponse();
        response.setId(weatherData.getId());
        response.setPincode(weatherData.getPincode());
       // response.setGeoCodingResponse(new GeoCodingResponse(weatherData.getLatitude(), weatherData.getLongitude()));
        response.setForDate(weatherData.getForDate());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            WeatherResponse weatherResponse = objectMapper.readValue(weatherData.getWeatherData(), WeatherResponse.class);
            response.setWeatherData(weatherResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return response;
    }

    private WeatherData fetchAndSaveWeatherData(String pincode, LocalDate forDate) {
        String countryCode ="IN";
        String geocodeUrl = String.format("%s?zip=%s,%s&appid=%s", geocodingApiUrl, pincode, countryCode, apiKey);
        GeoCodingResponse geocodeResponse = restTemplate.getForObject(geocodeUrl, GeoCodingResponse.class);

        Double latitude =geocodeResponse.getLat();
        Double longitude =geocodeResponse.getLon();

        String weatherUrl = String.format("%s?lat=%f&lon=%f&appid=%s", weatherApiUrl, latitude, longitude, apiKey);
        String weatherData = restTemplate.getForObject(weatherUrl, String.class);

        WeatherData data = new WeatherData();
        data.setPincode(pincode);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        data.setForDate(forDate);
        data.setWeatherData(weatherData);
        return repo.save(data);



    }
}
