package ru.job4j.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService room;

    public RoomController(RoomService room) {
        this.room = room;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return room.findAll();
    }

    @GetMapping("/{id}")
    public Room findRoomById(@PathVariable int id) {
        return room.findById(id).orElse(new Room());
    }
}
