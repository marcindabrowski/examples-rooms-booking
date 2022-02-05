package com.github.marcindabrowski.example.roomsbooking.application.rest;

import com.github.marcindabrowski.example.roomsbooking.domain.model.RoomNightOccupancy;

public record BookRoomsResponse(RoomNightOccupancy economy, RoomNightOccupancy premium) {}
