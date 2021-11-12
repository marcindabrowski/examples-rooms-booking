package com.github.marcindabrowski.example.roomsbooking.config;

import com.github.marcindabrowski.example.roomsbooking.domain.service.BookingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookingConfiguration {

    @Bean
    BookingService bookingService(BookingServiceConfiguration bookingServiceConfiguration) {
        return new BookingService(bookingServiceConfiguration.premiumRoomMinimumPrice());
    }
}
