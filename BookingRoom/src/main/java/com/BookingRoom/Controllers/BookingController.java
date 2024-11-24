package com.BookingRoom.Controllers;

import com.BookingRoom.DTO.BookingDTO;
import com.BookingRoom.Entity.Booking;
import com.BookingRoom.Entity.Room;
import com.BookingRoom.Entity.Users;
import com.BookingRoom.Service.IBookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingDTO> bookRoom(
            @RequestParam Long userId,
            @RequestParam Long roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        Booking booking = bookingService.bookRoom(userId, roomId, checkInDate, checkOutDate);
        BookingDTO bookingDTO = convertToDto(booking);
        return ResponseEntity.ok(bookingDTO);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBookingDTO = bookingService.updateBooking(bookingId, bookingDTO);
        if (updatedBookingDTO != null) {
            return ResponseEntity.ok(updatedBookingDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/cancel/{bookingId}")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam String cancellationDate) {
        LocalDate cancellationDateParsed = LocalDate.parse(cancellationDate);
        String response = bookingService.cancelBooking(bookingId, cancellationDateParsed);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long bookingId) {
        BookingDTO bookingDTO = bookingService.getBookingById(bookingId);
        if (bookingDTO != null) {
            return ResponseEntity.ok(bookingDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    private BookingDTO convertToDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setRoomId(booking.getRoom().getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        return bookingDTO;
    }

    private Booking convertToEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setUser(new Users(bookingDTO.getUserId()));
        booking.setRoom(new Room(bookingDTO.getRoomId()));
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        return booking;
    }
}
