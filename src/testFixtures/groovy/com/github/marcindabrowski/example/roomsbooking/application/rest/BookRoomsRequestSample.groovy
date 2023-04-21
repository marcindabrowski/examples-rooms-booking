package com.github.marcindabrowski.example.roomsbooking.application.rest

class BookRoomsRequestSample {
    static final Map<String, Object> DEFAULT_PROPERTIES = [
        freeEconomyRooms: 1,
        freePremiumRooms: 1,
        potentialGuests : [23, 45, 155, 374, 22, 99.99, 100, 101, 115, 209]
    ] as Map<String, Object>

    static Map<String, Object> aBookRoomsRequest(Map<String, Object> customProperties = [:]) {
        Map<String, Object> props = DEFAULT_PROPERTIES + customProperties
        return [
            freeRooms      : [
                economy: props.freeEconomyRooms,
                premium: props.freePremiumRooms,
            ],
            potentialGuests: props.potentialGuests,
        ]
    }
}
