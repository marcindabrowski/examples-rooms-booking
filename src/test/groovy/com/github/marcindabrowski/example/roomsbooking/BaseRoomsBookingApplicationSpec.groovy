package com.github.marcindabrowski.example.roomsbooking

import com.github.marcindabrowski.example.roomsbooking.domain.model.Amount
import com.github.marcindabrowski.example.roomsbooking.domain.service.BookingService
import spock.lang.Specification

abstract class BaseRoomsBookingApplicationSpec extends Specification {
    Amount premiumRoomMinimumPrice = Amount.of(100)
    BookingService bookingService = new BookingService(premiumRoomMinimumPrice)
}
