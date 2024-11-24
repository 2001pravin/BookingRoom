package com.BookingRoom.Service;

import java.time.LocalDate;
import java.util.List;

import com.BookingRoom.DTO.BookingDTO;
import com.BookingRoom.Entity.Booking;

public interface IBookingService {
    Booking bookRoom(Long userId, Long roomId, LocalDate checkIn, LocalDate checkOut);

    String cancelBooking(Long bookingId, LocalDate cancellationDate);

    BookingDTO getBookingById(Long bookingId);

    List<BookingDTO> getAllBookings();

    BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO);
}
