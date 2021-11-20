package ru.job4j.passports.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.passports.domain.Passport;
import ru.job4j.passports.repository.PassportRepo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PassportService {
    private final PassportRepo repo;

    public PassportService(PassportRepo repo) {
        this.repo = repo;
    }

    public Passport save(Passport passport) {
        return repo.save(passport);
    }

    public List<Passport> findAll() {
        return (List<Passport>) repo.findAll();
    }

    public Optional<Passport> findById(int id) {
        return repo.findById(id);
    }

    public Passport update(Passport passport, Passport current)
            throws InvocationTargetException, IllegalAccessException {
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method : methods) {
            var name = method.getName();
            if (name.startsWith("get") || name.startsWith("set")) {
                namePerMethod.put(name, method);
            }
        }
        for (var name : namePerMethod.keySet()) {
            if (name.startsWith("get")) {
                var getMethod = namePerMethod.get(name);
                var setMethod = namePerMethod.get(name.replace("get", "set"));
                if (setMethod == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Invalid properties mapping");
                }
                var newValue = getMethod.invoke(passport);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        return repo.save(passport);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }

    public List<Passport> findBySerial(int serial) {
        return repo.findPassportBySerial(serial);
    }

    public List<Passport> findExpirePassports() {
        return repo.findPassportWithExpireDate();
    }

    public List<Passport> findReplaceable() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 3);
        return repo.findReplaceablePassports(calendar.getTime());
    }
}