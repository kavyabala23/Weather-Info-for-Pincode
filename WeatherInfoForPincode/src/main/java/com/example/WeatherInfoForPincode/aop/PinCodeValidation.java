package com.example.WeatherInfoForPincode.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Aspect
public class PinCodeValidation {

    @Before("execution(* com.example.WeatherInfoForPincode.service.WeatherService.getWeather(..)) && args(pincode, forDate)")
    public void validatePincode(JoinPoint joinPoint, String pincode, LocalDate forDate) {
        // Validate pincode length
        if (pincode.length() != 6 || !pincode.matches("\\d{6}")) {
            System.out.println("Enter 6 digit");
            throw new IllegalArgumentException("Pincode must be a 6-digit number.");
        }
    }
}
