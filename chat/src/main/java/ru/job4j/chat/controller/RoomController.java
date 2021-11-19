package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.model.RoomDTO;
import ru.job4j.chat.service.RoomService;

import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/room")
public class RoomController {
    private final RoomService rooms;

    public RoomController(RoomService room) {
        this.rooms = room;
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Room name can't be empty");
        }
        return ResponseEntity.of(Optional.of(this.rooms.save(room)));
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
        return new ResponseEntity(
                body,
                new MultiValueMapAdapter<>(Map.of("Job4jCustomHeader", List.of("job4j"))),
                HttpStatus.OK
        );
    }

    @PatchMapping("/")
    public ResponseEntity<?> update(@Valid @RequestBody RoomDTO dto) {
        var currentRoom = rooms.findById(dto.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Room is not found."));
        return ResponseEntity.of(Optional.of(rooms.update(currentRoom, dto)));
    }
}