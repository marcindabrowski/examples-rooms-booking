package com.github.marcindabrowski.example.roomsbooking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "booking-service")
@ConstructorBinding
public record BookingServiceConfiguration(BigDecimal premiumRoomMinimumPrice) {}
