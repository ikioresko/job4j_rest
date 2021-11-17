package ru.job4j.chat.controller;

import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messages;

    public MessageController(MessageService messages) {
        this.messages = messages;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return messages.findAll();
    }

    @GetMapping("/{id}")
    public Message findRoomById(@PathVariable int id) {
        return messages.findById(id).orElse(new Message());
    }

    @PostMapping("/")
    public Message create(Message message) {
        return messages.save(message);
    }
}