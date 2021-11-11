package com.github.marcindabrowski.example.roomsbooking

import com.github.marcindabrowski.example.roomsbooking.domain.service.BookingService
import spock.lang.Specification

abstract class BaseRoomsBookingApplicationSpec extends Specification{
    BigDecimal premiumRoomMinimumPrice = 100
    BookingService bookingService = new BookingService(premiumRoomMinimumPrice)
}
