package com.github.marcindabrowski.example.roomsbooking.domain.service;

import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelFreeRooms;
import com.github.marcindabrowski.example.roomsbooking.domain.model.HotelRoomsNightOccupancy;
import com.github.marcindabrowski.example.roomsbooking.domain.model.PotentialGuest;
import com.github.marcindabrowski.example.roomsbooking.domain.model.RoomNightOccupancy;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

final class HotelRoomsBooking {
    private static final RoomNightOccupancy NOT_BOOKED_ROOMS = new RoomNightOccupancy(0, BigDecimal.ZERO);

    private final BigDecimal premiumRoomMinimumPrice;
    private final HotelFreeRooms freeRooms;
    private final List<PotentialGuest> economyPotentialGuests;
    private final List<PotentialGuest> premiumPotentialGuests;

    HotelRoomsBooking(BigDecimal premiumRoomMinimumPrice, HotelFreeRooms freeRooms, List<PotentialGuest> potentialGuestsList) {
        this.premiumRoomMinimumPrice = premiumRoomMinimumPrice;
        this.freeRooms = freeRooms;
        this.economyPotentialGuests = potentialGuestsList.stream()
                .filter(this::isEconomyGuest)
                .sorted(Comparator.comparing(PotentialGuest::guestPayment).reversed())
                .collect(Collectors.toList());
        this.premiumPotentialGuests = potentialGuestsList.stream()
                .filter(this::isPremiumGuest)
                .sorted(Comparator.comparing(PotentialGuest::guestPayment).reversed())
                .collect(Collectors.toList());
    }

    HotelRoomsNightOccupancy bookRooms() {
        RoomNightOccupancy bookedPremiumRooms = bookPremiumRoomsByPremiumGuests();
        if (shouldEconomyGuestsBookPremiumRooms(bookedPremiumRooms)) {
            bookedPremiumRooms = bookPremiumRoomsByEconomyGuests(bookedPremiumRooms);
        }
        RoomNightOccupancy bookedEconomyRooms = bookEconomyRooms();
        return new HotelRoomsNightOccupancy(bookedEconomyRooms, bookedPremiumRooms);
    }

    private boolean shouldEconomyGuestsBookPremiumRooms(RoomNightOccupancy bookedPremiumRooms) {
        return freeRooms.economy() < economyPotentialGuests.size()
                && freeRooms.premium() > bookedPremiumRooms.bookedRooms();
    }

    private RoomNightOccupancy bookPremiumRoomsByPremiumGuests() {
        return bookRooms(freeRooms.premium(), NOT_BOOKED_ROOMS, this.premiumPotentialGuests.iterator());
    }

    private RoomNightOccupancy bookPremiumRoomsByEconomyGuests(RoomNightOccupancy bookedPremiumRooms) {
        return bookRooms(freeRooms.premium(), bookedPremiumRooms, this.economyPotentialGuests.iterator());
    }

    private RoomNightOccupancy bookEconomyRooms() {
        return bookRooms(freeRooms.economy(), NOT_BOOKED_ROOMS, this.economyPotentialGuests.iterator());
    }

    private RoomNightOccupancy bookRooms(int freeRooms, RoomNightOccupancy alreadyBookedRooms, Iterator<PotentialGuest> guestIterator) {
        int bookedRooms = alreadyBookedRooms.bookedRooms();
        BigDecimal bookingAmount = alreadyBookedRooms.bookingAmount();
        while (bookedRooms < freeRooms && guestIterator.hasNext()) {
            bookedRooms++;
            bookingAmount = bookingAmount.add(guestIterator.next().guestPayment());
            guestIterator.remove();
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
