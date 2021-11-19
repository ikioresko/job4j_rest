package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService role) {
        this.roleService = role;
    }

    @PostMapping("/")
    public Role create(@Valid @RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("Room name can't be empty");
        }
        return roleService.save(role);
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role findRoleById(@PathVariable int id) {
        return roleService.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Role is not found."
        ));
    }

    @PatchMapping("/")
    public Role update(@Valid  @RequestBody Role role)
            throws InvocationTargetException, IllegalAccessException {
        return roleService.update(role, findRoleById(role.getId()));
    }
}
