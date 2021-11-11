package com.github.marcindabrowski.example.roomsbooking.domain.service

import com.github.marcindabrowski.example.roomsbooking.BaseRoomsBookingApplicationSpec
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest
import spock.lang.Unroll

class BookingServiceTest extends BaseRoomsBookingApplicationSpec {

    @Unroll
    def "should book rooms - #testCaseName"() {
        given: "number of free rooms"
            HotelFreeRooms freeRooms = new HotelFreeRooms(freeEconomyRooms, freePremiumRooms)

        and: "list of potential guests"
            List<PotentialGuest> potentialGuestsList = potentialGuestsPayments.collect { new PotentialGuest(BigDecimal.valueOf(it)) }.toList()

        when: "book rooms"
            HotelRoomsNightOccupancy hotelRoomsNightOccupancy = bookingService.bookRooms(freeRooms, potentialGuestsList)

        then: "the most effective bookings is used"
            HotelRoomsNightOccupancyAssertion.assertThat(hotelRoomsNightOccupancy) {
                hasBookedEconomyRooms(bookedRooms: economyRoomsBooked, bookingAmount: economyRoomsAmount)
                hasBookedPremiumRooms(bookedRooms: premiumRoomsBooked, bookingAmount: premiumRoomsAmount)
            }

        where:
            testCaseName                  | freeEconomyRooms | freePremiumRooms | potentialGuestsPayments                           | economyRoomsBooked | economyRoomsAmount | premiumRoomsBooked | premiumRoomsAmount
            "1 of 2 economy rooms booked" | 2                | 1                | [99, 100]                                         | 1                  | 99                 | 1                  | 1
            "2 of 2 economy rooms booked" | 2                | 1                | [99, 23, 36, 100]                                 | 2                  | 135                | 1                  | 1
            "No economy rooms booked"     | 1                | 1                | [100, 100]                                        | 0                  | 0                  | 1                  | 1
            "Test Case 1"                 | 3                | 3                | [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209] | 3                  | 167.99                  | 1                  | 1
            "Test Case 2"                 | 5                | 3                | [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209] | 4                  | 189.99                  | 1                  | 1
            "Test Case 3"                 | 4                | 3                | [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209] | 4                  | 189.99                  | 1                  | 1
            "Test Case 4"                 | 1                | 3                | [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209] | 1                  | 99.99                  | 1                  | 1
    }
}
