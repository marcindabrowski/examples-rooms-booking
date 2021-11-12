package com.github.marcindabrowski.example.roomsbooking.application.rest;

import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy;
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;
import com.github.marcindabrowski.example.roomsbooking.domain.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/book-rooms")
@RequiredArgsConstructor
class BookingController {
    private final BookingService bookingService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BookRoomsResponse bookRooms(@RequestBody final BookRoomsRequest bookRoomsRequest) {
        HotelRoomsNightOccupancy hotelRoomsNightOccupancy = bookingService.bookRooms(
                bookRoomsRequest.freeRooms(),
                bookRoomsRequest.potentialGuests().stream().map(this::potentialGuest).toList());
        return new BookRoomsResponse(hotelRoomsNightOccupancy.economy(), hotelRoomsNightOccupancy.premium());
    }

    private PotentialGuest potentialGuest(double potentialGuestAmount) {
        return new PotentialGuest(BigDecimal.valueOf(potentialGuestAmount));
    }
}
