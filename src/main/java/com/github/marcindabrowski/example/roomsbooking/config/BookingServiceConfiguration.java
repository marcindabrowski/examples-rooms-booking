package com.github.marcindabrowski.example.roomsbooking.config;

import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "booking-service")
public record BookingServiceConfiguration(BigDecimal premiumRoomMinimumPrice) {}
