package ru.job4j.auth.controller;

import org.springframework.web.bind.annotation.*;
import ru.job4j.auth.model.Employee;

import ru.job4j.auth.model.Person;
import ru.job4j.auth.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employees;

    public EmployeeController(final EmployeeService employees) {
        this.employees = employees;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return employees.findAll();
    }

    @PostMapping("/{id}")
    public Employee addAccount(@PathVariable int id, @RequestBody Person person) {
        Employee employee = employees.findById(id).orElse(null);
        if (employee != null) {
            employee.addPerson(person);
        }
        employees.save(employee);
        return employee;
    }

    @PutMapping("/{id}")
    public Employee editAccount(@PathVariable int id, @RequestBody Person person) {
        Employee employee = employees.findById(id).orElse(null);
        if (employee != null) {
            employee.getPersonSet().remove(person);
            employee.addPerson(person);
        }
        employees.save(employee);
        return employee;
    }

    @DeleteMapping("/{id}")
    public Employee deleteAllAccount(@PathVariable int id) {
        Employee employee = employees.findById(id).orElse(null);
        if (employee != null) {
            employee.getPersonSet().clear();
        }
        employees.save(employee);
        return employee;
    }
}
