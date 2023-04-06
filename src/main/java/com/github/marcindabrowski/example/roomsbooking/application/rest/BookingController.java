package com.github.marcindabrowski.example.roomsbooking.application.rest;

import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;
import com.github.marcindabrowski.example.roomsbooking.domain.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.val;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-rooms")
@RequiredArgsConstructor
class BookingController {
    private final BookingService bookingService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRoomsResponse bookRooms(@RequestBody final BookRoomsRequest bookRoomsRequest) {
        val hotelRoomsNightOccupancy =
                bookingService.bookRooms(
                        bookRoomsRequest.freeRooms(),
                        bookRoomsRequest.potentialGuests().stream()
                                .map(PotentialGuest::new)
                                .toList());
        return new BookRoomsResponse(
                hotelRoomsNightOccupancy.economy(), hotelRoomsNightOccupancy.premium());
    }
}
