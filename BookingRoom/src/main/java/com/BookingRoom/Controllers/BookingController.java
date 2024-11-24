package com.BookingRoom.Controllers;

import org.springframework.stereotype.Controller;

import com.BookingRoom.DTO.BookingDTO;
import com.BookingRoom.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<BookingDTO> bookRoom(
            @RequestParam Long userId,
            @RequestParam Long roomId,
            @RequestParam String checkIn,
            @RequestParam String checkOut) {
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        BookingDTO bookingDTO = bookingService.toDto(bookingService.bookRoom(userId, roomId, checkInDate, checkOutDate));
        return ResponseEntity.ok(bookingDTO);
    }

    @PutMapping("/update/{bookingId}")
    public ResponseEntity<BookingDTO> updateBooking(
            @PathVariable Long bookingId,
            @RequestBody BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBooking(bookingId, bookingDTO);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
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
}
