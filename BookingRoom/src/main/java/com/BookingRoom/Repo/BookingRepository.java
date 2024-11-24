package com.BookingRoom.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BookingRoom.Entity.Booking;
import com.BookingRoom.Entity.Users;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(Users user);


}
