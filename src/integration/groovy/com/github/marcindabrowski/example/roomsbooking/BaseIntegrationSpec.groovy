package com.github.marcindabrowski.example.roomsbooking

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(classes = [RoomsBookingApplication],
        properties = "application.environment=integration",
        webEnvironment = RANDOM_PORT)
abstract class BaseIntegrationSpec extends Specification {
}
