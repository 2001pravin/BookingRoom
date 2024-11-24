package com.BookingRoom.Service;

import com.BookingRoom.Entity.Room;
import com.BookingRoom.Repo.RoomRepository;
import com.BookingRoom.Exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements IRoomServices {
    
    @Autowired
    private RoomRepository roomRepository;

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long roomId, Room roomDetails) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        existingRoom.setRoomNumber(roomDetails.getRoomNumber());
        existingRoom.setAvailable(roomDetails.isAvailable());
        return roomRepository.save(existingRoom);
    }

    public boolean deleteRoom(Long roomId) {
        Room existingRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        roomRepository.delete(existingRoom);
        return true;
    }

    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
