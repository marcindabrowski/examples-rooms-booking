package com.github.marcindabrowski.example.roomsbooking.application.rest;

import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms;

import java.util.List;

public record BookRoomsRequest(HotelFreeRooms freeRooms, List<Double> potentialGuests) {
}
