package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService rooms;

    public RoomController(RoomService room) {
        this.rooms = room;
    }

    @PostMapping("/")
    public Room create(@RequestBody Room room) {
        System.out.println(room);
        if (room.getName() == null) {
            throw new NullPointerException("Room name can't be empty");
        }
        return this.rooms.save(room);
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return rooms.findAll();
    }

    @GetMapping("/{id}")
    public Room findRoomById(@PathVariable int id) {
        return rooms.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Room is not found."));
    }
}
