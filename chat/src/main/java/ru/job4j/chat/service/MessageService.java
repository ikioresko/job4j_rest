package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.MessageDTO;
import ru.job4j.chat.repository.MessageRepo;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepo store;

    public MessageService(MessageRepo store) {
        this.store = store;
    }

    public List<Message> findAll() {
        return (List<Message>) store.findAll();
    }

    public Optional<Message> findById(int id) {
        return store.findById(id);
    }

    public Message save(Message message) {
        return store.save(message);
    }

    public Message update(Message currentMessage, MessageDTO newMessage) {
        var author = currentMessage.getAuthor();
        var message = new Message();
        message.setAuthor(author);
        message.setText(newMessage.getText());
        message.setId(newMessage.getId());
        return store.save(message);
    }

    public void deleteById(int id) {
        Message message = new Message();
        message.setId(id);
        store.delete(message);
    }
}