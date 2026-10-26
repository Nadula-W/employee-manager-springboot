
package com.example.employee_manager.service;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Read all employees
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Read employee by ID
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Employee not found"
                )
            );
    }

    // Create employee
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    // Check for duplicate email
    public boolean emailExists(String email) {
        return repository.existsByEmail(email);
    }

    // Check duplicate email during update
    public boolean emailExistsForAnother(
            String email, Long id) {

        return repository.existsByEmailAndIdNot(email, id);
    }

    // Update employee
    public Employee updateEmployee(Long id, Employee updated) {

        Employee existing = getEmployeeById(id);

        existing.setFullName(updated.getFullName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setDepartment(updated.getDepartment());
        existing.setSalary(updated.getSalary());

        return repository.save(existing);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        getEmployeeById(id);
        repository.deleteById(id);
    }
}
