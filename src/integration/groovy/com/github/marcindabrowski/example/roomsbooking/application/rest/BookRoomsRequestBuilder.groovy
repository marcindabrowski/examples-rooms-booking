package com.github.marcindabrowski.example.roomsbooking.application.rest

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

@Builder(prefix = "with", builderStrategy = SimpleStrategy)
class BookRoomsRequestBuilder {
    int freeEconomyRooms = 1
    int freePremiumRooms = 1
    List<Double> potentialGuests = [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]

    static BookRoomsRequestBuilder aBookRoomsRequest() {
        return new BookRoomsRequestBuilder()
    }

    Map<String, Object> toJson() {
        return [
                freeRooms      : [
                        economy: freeEconomyRooms,
                        premium: freePremiumRooms,
                ],
                potentialGuests: potentialGuests,
        ]
    }
}
