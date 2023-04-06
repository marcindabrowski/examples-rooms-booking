package com.github.marcindabrowski.example.roomsbooking.domain.service;

import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms;
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy;
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class BookingService {

    private final BigDecimal premiumRoomMinimumPrice;

    public HotelRoomsNightOccupancy bookRooms(
            @NonNull HotelFreeRooms freeRooms, @NonNull List<PotentialGuest> potentialGuestsList) {
        val hotelRoomsBooking =
                new HotelRoomsBooking(this.premiumRoomMinimumPrice, freeRooms, potentialGuestsList);
        return hotelRoomsBooking.bookRooms();
    }
}
