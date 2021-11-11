package com.github.marcindabrowski.example.roomsbooking.domain.model;

import java.math.BigDecimal;

public record RoomNightOccupancy(int bookedRooms, BigDecimal bookingAmount) {
}
