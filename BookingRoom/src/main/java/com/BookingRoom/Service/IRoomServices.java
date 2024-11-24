package com.BookingRoom.Service;

import java.util.List;

import com.BookingRoom.Entity.Room;

public interface IRoomServices {
    public Room addRoom(Room room);
    public Room updateRoom(Long roomId, Room roomDetails);
    public boolean deleteRoom(Long roomId);
    public Room getRoomById(Long roomId);
     public List<Room> getAllRooms();

}
