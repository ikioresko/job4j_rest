package ru.job4j.passports.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.passports.domain.Passport;
import ru.job4j.passports.service.PassportService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/passports")
public class PassportController {
    private final PassportService passportService;

    public PassportController(PassportService passportService) {
        this.passportService = passportService;
    }

    @PostMapping("/")
    public Passport save(@Valid @RequestBody Passport passport) {
        return passportService.save(passport);
    }

    @GetMapping("/")
    public List<Passport> findAll() {
        return passportService.findAll();
    }

    @PutMapping("/{id}")
    public Passport update(@PathVariable int id, @Valid @RequestBody Passport passport)
            throws InvocationTargetException, IllegalAccessException {
        var current = findById(id);
        passport.setId(id);
        return passportService.update(passport, current);
    }

    @GetMapping("/{id}")
    public Passport findById(@PathVariable int id) {
        return passportService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Passport with id = " + id + " is not found."
        ));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        passportService.delete(id);
    }

    @GetMapping("/serial/{id}")
    public List<Passport> findBySerial(@PathVariable int id) {
        return passportService.findBySerial(id);
    }

    @GetMapping("/unavailable")
    public List<Passport> findExpirePassports() {
        return passportService.findExpirePassports();
    }

    @GetMapping("/replaceable")
    public List<Passport> findReplaceable() {
        return passportService.findReplaceable();
    }
}