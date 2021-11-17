package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepo;

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

    public void deleteById(int id) {
        Role role = new Role();
        role.setId(id);
        store.delete(role);
    }
}
