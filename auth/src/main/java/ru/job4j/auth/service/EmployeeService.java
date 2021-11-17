package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.repository.EmployeeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepo store;

    public EmployeeService(EmployeeRepo store) {
        this.store = store;
    }

    public List<Employee> findAll() {
        return (List<Employee>) store.findAll();
    }

    public Optional<Employee> findById(int id) {
        return store.findById(id);
    }

    public Employee save(Employee employee) {
        return store.save(employee);

    }

    public void deleteById(int id) {
        Employee employee = new Employee();
        employee.setId(id);
        store.delete(employee);
    }
}