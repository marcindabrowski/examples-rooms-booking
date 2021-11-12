package com.github.marcindabrowski.example.roomsbooking;

import com.github.marcindabrowski.example.roomsbooking.config.BookingServiceConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(BookingServiceConfiguration.class)
@SpringBootApplication
public class RoomsBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomsBookingApplication.class, args);
    }
}
