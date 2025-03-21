package com.github.marcindabrowski.example.roomsbooking.application.rest

import com.github.marcindabrowski.example.roomsbooking.BaseIntegrationSpec
import com.github.marcindabrowski.example.roomsbooking.infrastructure.ability.RestApiAbility
import org.springframework.http.ResponseEntity

import static com.github.marcindabrowski.example.roomsbooking.application.rest.BookRoomsRequestSample.aBookRoomsRequest
import static org.springframework.http.HttpStatus.OK

class BookingControllerSpec extends BaseIntegrationSpec implements RestApiAbility {

    def "test bookRooms REST API endpoint - #testCaseName"() {
        given: "booking rooms request"
            Map<String, Object> request = aBookRoomsRequest([
                freeEconomyRooms: freeEconomyRooms,
                freePremiumRooms: freePremiumRooms,
            ])

        when: "book hotel rooms"
            ResponseEntity<Map> responseEntity = bookRooms(request)

        then: "the most effective bookings is used"
            BookRoomsResponseAssert.assertThat(responseEntity) {
                hasStatusCode(OK)
                hasBookedEconomyRooms(bookedRooms: economyRoomsBooked, bookingAmount: economyRoomsAmount)
                hasBookedPremiumRooms(bookedRooms: premiumRoomsBooked, bookingAmount: premiumRoomsAmount)
            }

        where:
            testCaseName  | freeEconomyRooms | freePremiumRooms | economyRoomsBooked | economyRoomsAmount | premiumRoomsBooked | premiumRoomsAmount
            "Test Case 1" | 3                | 3                | 3                  | 167.99             | 3                  | 738
            "Test Case 2" | 5                | 7                | 4                  | 189.99             | 6                  | 1054
            "Test Case 3" | 4                | 2                | 4                  | 189.99             | 2                  | 583
            "Test Case 4" | 1                | 7                | 1                  | 45                 | 7                  | 1153.99
    }
}
