package com.github.marcindabrowski.example.roomsbooking.application.rest

import com.github.marcindabrowski.example.roomsbooking.BaseIntegrationTest
import com.github.marcindabrowski.example.roomsbooking.infrastructure.ability.RestApiAbility
import org.springframework.http.ResponseEntity
import spock.lang.Unroll

import static com.github.marcindabrowski.example.roomsbooking.application.rest.BookRoomsRequestBuilder.aBookRoomsRequest
import static org.springframework.http.HttpStatus.OK

class BookingControllerSpec extends BaseIntegrationTest implements RestApiAbility {

    @Unroll
    def "test bookRooms REST API endpoint - #testCaseName"() {
        given: "booking rooms request"
            BookRoomsRequestBuilder request = aBookRoomsRequest()
                    .withFreeEconomyRooms(freeEconomyRooms)
                    .withFreePremiumRooms(freePremiumRooms)

        when: "book hotel rooms"
            ResponseEntity<Map> responseEntity = bookRooms(request.toJson())

        then: "the most effective bookings is used"
            BookRoomsResponseAssertion.assertThat(responseEntity) {
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
