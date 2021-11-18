package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public Message findMessageById(@PathVariable int id) {
        return messages.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Message with id = " + id + " is not found."
        ));
    }

    @PostMapping("/")
    public Message create(@RequestBody Message message) {
        if (message.getText() == null) {
            throw new NullPointerException("Message can't be empty");
        }
        return messages.save(message);
    }
}