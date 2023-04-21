package com.github.marcindabrowski.example.roomsbooking.domain.model

import groovy.transform.NamedVariant

class HotelRoomsNightOccupancyAssert {

    static class HotelRoomsNightOccupancyAssertionBuilder {
        private final HotelRoomsNightOccupancy hotelRoomsNightOccupancy

        HotelRoomsNightOccupancyAssertionBuilder(HotelRoomsNightOccupancy hotelRoomsNightOccupancy) {
            this.hotelRoomsNightOccupancy = hotelRoomsNightOccupancy
        }

        @NamedVariant
        void hasBookedEconomyRooms(int bookedRooms, BigDecimal bookingAmount) {
            assert hotelRoomsNightOccupancy.economy().bookedRooms() == bookedRooms
            assert hotelRoomsNightOccupancy.economy().bookingAmount().value() == bookingAmount
        }

        @NamedVariant
        void hasBookedPremiumRooms(int bookedRooms, BigDecimal bookingAmount) {
            assert hotelRoomsNightOccupancy.premium().bookedRooms() == bookedRooms
            assert hotelRoomsNightOccupancy.premium().bookingAmount().value() == bookingAmount
        }
    }

    static void assertThat(HotelRoomsNightOccupancy hotelRoomsNightOccupancy, @DelegatesTo(HotelRoomsNightOccupancyAssertionBuilder) Closure closure) {
        new HotelRoomsNightOccupancyAssertionBuilder(hotelRoomsNightOccupancy).with(closure)
    }
}
