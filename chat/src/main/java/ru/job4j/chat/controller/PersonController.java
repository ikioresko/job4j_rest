package ru.job4j.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService person;

    public PersonController(PersonService person) {
        this.person = person;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        return person.findAll();
    }

    @GetMapping("/{id}")
    public Person findRoomById(@PathVariable int id) {
        return person.findById(id).orElse(new Person());
    }
}
