package com.github.marcindabrowski.example.roomsbooking.domain.service;

import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;
import com.github.marcindabrowski.example.roomsbooking.domain.model.RoomNightOccupancy;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

final class PotentialGuests {
    private final BigDecimal premiumRoomMinimumPrice;
    private final List<PotentialGuest> economyPotentialGuests;
    private final List<PotentialGuest> premiumPotentialGuests;

    PotentialGuests(@NonNull List<PotentialGuest> potentialGuestsList, @NonNull BigDecimal premiumRoomMinimumPrice) {
        this.premiumRoomMinimumPrice = premiumRoomMinimumPrice;
        this.economyPotentialGuests = potentialGuestsList.stream()
                .filter(this::isEconomyGuest)
                .sorted(Comparator.comparing(PotentialGuest::guestPayment).reversed())
                .collect(Collectors.toList());
        this.premiumPotentialGuests = potentialGuestsList.stream()
                .filter(this::isPremiumGuest)
                .sorted(Comparator.comparing(PotentialGuest::guestPayment).reversed())
                .collect(Collectors.toList());
    }

    RoomNightOccupancy bookEconomyRooms(int freeRooms) {
        return bookRooms(freeRooms, 0, this.economyPotentialGuests.iterator());
    }

    private RoomNightOccupancy bookRooms(int freeRooms, int alreadyBookedRooms, Iterator<PotentialGuest> guestIterator) {
        int bookedRooms = alreadyBookedRooms;
        BigDecimal bookingAmount = BigDecimal.ZERO;
        while (bookedRooms < freeRooms && guestIterator.hasNext()) {
            bookedRooms++;
            bookingAmount = bookingAmount.add(guestIterator.next().guestPayment());
        }
        return new RoomNightOccupancy(bookedRooms, bookingAmount);
    }

    private boolean isEconomyGuest(PotentialGuest potentialGuest) {
        return !isPremiumGuest(potentialGuest);
    }

    private boolean isPremiumGuest(PotentialGuest potentialGuest) {
        return potentialGuest.guestPayment().compareTo(premiumRoomMinimumPrice) >= 0;
    }
}
