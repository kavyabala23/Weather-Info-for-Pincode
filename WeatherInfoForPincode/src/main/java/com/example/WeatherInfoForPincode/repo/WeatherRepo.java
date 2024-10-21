package com.example.WeatherInfoForPincode.repo;

import com.example.WeatherInfoForPincode.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherRepo extends JpaRepository<WeatherData,Integer> {
    Optional<WeatherData> findByPincodeAndForDate(String pincode, LocalDate forDate);
}
