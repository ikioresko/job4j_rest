package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.model.PersonDTO;
import ru.job4j.chat.service.PersonService;

import javax.validation.Valid;
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
    public Person findPersonById(@PathVariable int id) {
        return person.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Person is not found."
        ));
    }

    @PatchMapping("/")
    public Person update(@Valid @RequestBody PersonDTO dto) {
        return person.update(findPersonById(dto.getId()), dto);
    }
}
