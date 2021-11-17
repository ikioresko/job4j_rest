package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

public interface PersonRepo extends CrudRepository<Person, Integer> {
    @Query("select p from Person p where p.username = ?1")
    Person findPersonByName(String name);
}