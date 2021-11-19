package ru.job4j.chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepo store;

    public RoleService(RoleRepo store) {
        this.store = store;
    }

    public List<Role> findAll() {
        return (List<Role>) store.findAll();
    }

    public Optional<Role> findById(int id) {
        return store.findById(id);
    }

    public Role save(Role role) {
        return store.save(role);

    }

    public Role update(Role role, Role current)
            throws InvocationTargetException, IllegalAccessException {
        var methods = current.getClass().getDeclaredMethods();
        var namePerMethod = new HashMap<String, Method>();
        for (var method: methods) {
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
                var newValue = getMethod.invoke(role);
                if (newValue != null) {
                    setMethod.invoke(current, newValue);
                }
            }
        }
        return store.save(role);
    }

    public void deleteById(int id) {
        Role role = new Role();
        role.setId(id);
        store.delete(role);
    }
}
