package com.BookingRoom.Controllers;

import com.BookingRoom.Entity.Room;
import com.BookingRoom.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room savedRoom = roomService.addRoom(room);
        return ResponseEntity.ok(savedRoom);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long roomId, @RequestBody Room roomDetails) {
        Room updatedRoom = roomService.updateRoom(roomId, roomDetails);
        if (updatedRoom != null) {
            return ResponseEntity.ok(updatedRoom);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        boolean isDeleted = roomService.deleteRoom(roomId);
        if (isDeleted) {
            return ResponseEntity.ok("Room deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room != null) {
            return ResponseEntity.ok(room);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
}
