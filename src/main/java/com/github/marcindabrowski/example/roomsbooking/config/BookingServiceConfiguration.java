package com.github.marcindabrowski.example.roomsbooking.config;

import com.github.marcindabrowski.example.roomsbooking.domain.model.Amount;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "booking-service")
public record BookingServiceConfiguration(Amount premiumRoomMinimumPrice) {}
