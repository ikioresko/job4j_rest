package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepo;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepo store;

    public PersonService(PersonRepo store) {
        this.store = store;
    }

    public List<Person> findAll() {
        return (List<Person>) store.findAll();
    }

    public Optional<Person> findById(int id) {
        return store.findById(id);
    }

    public Person save(Person person) {
        return store.save(person);

    }

    public void deleteById(int id) {
        Person person = new Person();
        person.setId(id);
        store.delete(person);
    }
}
