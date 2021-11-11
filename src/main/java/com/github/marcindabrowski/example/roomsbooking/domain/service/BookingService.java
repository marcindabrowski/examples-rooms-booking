package com.github.marcindabrowski.example.roomsbooking.domain.service;

import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms;
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy;
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;
import com.github.marcindabrowski.example.roomsbooking.domain.model.RoomNightOccupancy;

import java.math.BigDecimal;
import java.util.List;

public class BookingService {

    public HotelRoomsNightOccupancy bookRooms(HotelFreeRooms freeRooms, List<PotentialGuest> potentialGuests) {
        return new HotelRoomsNightOccupancy(
                new RoomNightOccupancy(1, BigDecimal.ONE),
                new RoomNightOccupancy(1, BigDecimal.ONE)
        );
    }
}
