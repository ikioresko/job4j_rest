package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.util.*;

@Controller
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findRoomById(@PathVariable int id) {
        return ResponseEntity.of(Optional.of(new HashSet<>() {{
            add(rooms.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Room is not found.")));
        }}));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        Object body = new HashSet<>() {{
            for (Room r : rooms.findAll()) {
                add(r);
            }
        }};
        var entity = new ResponseEntity(
                body,
                new MultiValueMapAdapter<>(Map.of("Job4jCustomHeader", List.of("job4j"))),
                HttpStatus.OK
        );
        return entity;
    }
}