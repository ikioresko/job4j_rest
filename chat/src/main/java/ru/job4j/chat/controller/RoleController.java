package ru.job4j.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;

import java.util.List;
@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService role;

    public RoleController(RoleService role) {
        this.role = role;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return role.findAll();
    }

    @GetMapping("/{id}")
    public Role findRoomById(@PathVariable int id) {
        return role.findById(id).orElse(new Role());
    }
}
