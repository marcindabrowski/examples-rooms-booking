package com.github.marcindabrowski.example.roomsbooking.domain.service

import com.github.marcindabrowski.example.roomsbooking.BaseRoomsBookingApplicationSpec
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest

class BookingServiceTest extends BaseRoomsBookingApplicationSpec {

    def "should book rooms"() {
        given: "number of free rooms"
            HotelFreeRooms freeRooms = new HotelFreeRooms(1, 1)

        and: "list of potential guests"
            List<PotentialGuest> potentialGuests = [new PotentialGuest(BigDecimal.valueOf(100)), new PotentialGuest(BigDecimal.valueOf(100))]

        when: "book rooms is called"
            HotelRoomsNightOccupancy hotelRoomsNightOccupancy = bookingService.bookRooms(freeRooms, potentialGuests)

        then: "the most effective bookings is used"
            HotelRoomsNightOccupancyAssertion.assertThat(hotelRoomsNightOccupancy) {
                hasBookedEconomyRooms(bookedRooms: 1, bookingAmount: 1)
                hasBookedPremiumRooms(bookedRooms: 1, bookingAmount: 1)
            }
    }
}
