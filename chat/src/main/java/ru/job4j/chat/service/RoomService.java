package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.model.RoomDTO;
import ru.job4j.chat.repository.RoomRepo;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private RoomRepo store;

    public RoomService(RoomRepo store) {
        this.store = store;
    }

    public List<Room> findAll() {
        return (List<Room>) store.findAll();
    }

    public Optional<Room> findById(int id) {
        return store.findById(id);
    }

    public Room save(Room room) {
        return store.save(room);
    }

    public void deleteById(int id) {
        Room room = new Room();
        room.setId(id);
        store.delete(room);
    }

    public Room update(Room currentRoom, RoomDTO dto) {
        var messages = currentRoom.getMessages();
        var room = new Room();
        room.setMessages(messages);
        room.setId(dto.getId());
        room.setName(dto.getName());
        return store.save(room);
    }
}
