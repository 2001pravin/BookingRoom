package com.BookingRoom.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookingRoom.DTO.BookingDTO;
import com.BookingRoom.Entity.Booking;
import com.BookingRoom.Entity.Room;
import com.BookingRoom.Entity.Users;
import com.BookingRoom.Repo.BookingRepository;
import com.BookingRoom.Repo.RoomRepository;
import com.BookingRoom.Exception.ResourceNotFoundException;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking bookRoom(Long userId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        
        if (!room.isAvailable()) {
            throw new IllegalArgumentException("Room is not available");
        }
        
        Booking booking = new Booking();
        booking.setUser(new Users(userId)); // Correct initialization with userId
        booking.setRoom(room);
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut); // Corrected to setCheckOutDate
        
        room.setAvailable(false);
        roomRepository.save(room);
        
        return bookingRepository.save(booking);
    }

    @Override
    public String cancelBooking(Long bookingId, LocalDate cancellationDate) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
        
        double refund = CancellationPolicy.calculateRefund(booking.getCheckInDate(), cancellationDate);
        bookingRepository.delete(booking);
        
        Room room = booking.getRoom();
        room.setAvailable(true);
        roomRepository.save(room);
        
        return "Booking canceled. Refund amount: " + refund;
    }

    @Override
    public BookingDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        return booking != null ? toDto(booking) : null;
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO updateBooking(Long bookingId, BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingId).orElse(null);
        
        if (existingBooking != null) {
            Booking booking = toEntity(bookingDTO);
            booking.setId(existingBooking.getId()); // Preserve the original ID
            Booking updatedBooking = bookingRepository.save(booking);
            return toDto(updatedBooking);
        }
        
        return null;
    }

    private BookingDTO toDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setUserId(booking.getUser().getId());
        bookingDTO.setRoomId(booking.getRoom().getId());
        bookingDTO.setCheckInDate(booking.getCheckInDate());
        bookingDTO.setCheckOutDate(booking.getCheckOutDate());
        return bookingDTO;
    }

    private Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setUser(new Users(bookingDTO.getUserId())); // Correct initialization with userId
        booking.setRoom(new Room(bookingDTO.getRoomId())); // Correct initialization with roomId
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        return booking;
    }
}
