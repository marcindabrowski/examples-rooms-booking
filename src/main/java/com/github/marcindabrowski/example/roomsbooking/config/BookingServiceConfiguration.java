package com.github.marcindabrowski.example.roomsbooking.config;

import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "booking-service")
@ConstructorBinding
public record BookingServiceConfiguration(BigDecimal premiumRoomMinimumPrice) {}
