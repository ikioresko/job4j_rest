package ru.job4j.client.controller;

import org.springframework.web.bind.annotation.*;
import ru.job4j.client.domain.Passport;
import ru.job4j.client.repository.PassportClientStore;

import java.util.List;

@RestController
@RequestMapping("/pass")
public class PassportControl {
    private final PassportClientStore store;

    public PassportControl(PassportClientStore passportService) {
        this.store = passportService;
    }

    @PostMapping("/")
    public Passport save(@RequestBody Passport passport) {
        return store.save(passport);
    }

    @GetMapping("/")
    public List<Passport> findAll() {
        return store.findAll();
    }

    @PutMapping("/{id}")
    public Passport update(@PathVariable int id, @RequestBody Passport passport) {
        return store.update(id, passport);
    }

    @GetMapping("/{id}")
    public Passport findById(@PathVariable int id) {
        return store.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        store.delete(id);
    }

    @GetMapping("/serial/{id}")
    public List<Passport> findBySerial(@PathVariable int id) {
        return store.findBySerial(id);
    }

    @GetMapping("/unavailable")
    public List<Passport> findExpirePassports() {
        return store.findExpirePassports();
    }

    @GetMapping("/replaceable")
    public List<Passport> findReplaceable() {
        return store.findReplaceable();
    }

    @GetMapping("/get")
    public List<Passport> work() {
        return store.findReplaceable();
    }
}