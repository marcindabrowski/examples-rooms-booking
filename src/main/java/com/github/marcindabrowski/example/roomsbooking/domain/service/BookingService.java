package com.github.marcindabrowski.example.roomsbooking.domain.service;

import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms;
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy;
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;
import com.github.marcindabrowski.example.roomsbooking.domain.model.RoomNightOccupancy;

import java.math.BigDecimal;
import java.util.List;

public class BookingService {
    private final BigDecimal premiumRoomMinimumPrice;

    public BookingService(BigDecimal premiumRoomMinimumPrice) {
        this.premiumRoomMinimumPrice = premiumRoomMinimumPrice;
    }

    public HotelRoomsNightOccupancy bookRooms(HotelFreeRooms freeRooms, List<PotentialGuest> potentialGuestsList) {
        PotentialGuests potentialGuests = new PotentialGuests(potentialGuestsList, this.premiumRoomMinimumPrice);
        return new HotelRoomsNightOccupancy(
                potentialGuests.bookEconomyRooms(freeRooms.economy()),
                new RoomNightOccupancy(1, BigDecimal.ONE)
        );
    }

}
