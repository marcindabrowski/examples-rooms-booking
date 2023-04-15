package com.github.marcindabrowski.example.roomsbooking.config;

import com.github.marcindabrowski.example.roomsbooking.domain.service.BookingService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingConfiguration {

  @Bean
  @ConfigurationPropertiesBinding
  AmountConverter amountConverter() {
    return new AmountConverter();
  }

  @Bean
  BookingService bookingService(BookingServiceConfiguration bookingServiceConfiguration) {
    return new BookingService(bookingServiceConfiguration.premiumRoomMinimumPrice());
  }
}
