package com.github.marcindabrowski.example.roomsbooking.application.rest

import groovy.transform.NamedVariant
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BookRoomsResponseAssert {

    static class BookRoomsResponseAssertionBuilder {
        private final ResponseEntity<Map> response

        BookRoomsResponseAssertionBuilder(ResponseEntity<Map> response) {
            this.response = response
        }

        void hasStatusCode(HttpStatus httpStatus) {
            assert response.statusCode == httpStatus
        }

        @NamedVariant
        void hasBookedEconomyRooms(int bookedRooms, BigDecimal bookingAmount) {
            assert response.body.economy.bookedRooms == bookedRooms
            assert response.body.economy.bookingAmount == bookingAmount.toPlainString()
        }

        @NamedVariant
        void hasBookedPremiumRooms(int bookedRooms, BigDecimal bookingAmount) {
            assert response.body.premium.bookedRooms == bookedRooms
            assert response.body.premium.bookingAmount == bookingAmount.toPlainString()
        }
    }

    static void assertThat(ResponseEntity<Map> response, @DelegatesTo(BookRoomsResponseAssertionBuilder) Closure closure) {
        new BookRoomsResponseAssertionBuilder(response).with(closure)
    }
}
