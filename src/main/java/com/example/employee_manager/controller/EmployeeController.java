
package com.example.employee_manager.controller;

import com.example.employee_manager.model.Employee;
import com.example.employee_manager.service.EmployeeService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Redirect home to employee list
    @GetMapping("/")
    public String home() {
        return "redirect:/employees";
    }

    // READ - Display employees
    @GetMapping("/employees")
    public String listEmployees(Model model) {

        model.addAttribute("employees",
                service.getAllEmployees());

        model.addAttribute("employee", new Employee());

        return "employees";
    }

    // CREATE - Save employee
    @PostMapping("/employees")
    public String createEmployee(
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (employee.getEmail() != null
                && service.emailExists(employee.getEmail())) {

            result.rejectValue(
                "email",
                "duplicate",
                "This email is already registered"
            );
        }

        if (result.hasErrors()) {
            model.addAttribute("employees",
                    service.getAllEmployees());
            return "employees";
        }

        service.saveEmployee(employee);

        redirectAttributes.addFlashAttribute(
            "success", "Employee saved successfully!"
        );

        return "redirect:/employees";
    }

    // EDIT - Display edit form
    @GetMapping("/employees/{id}/edit")
    public String editEmployee(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
            "employee",
            service.getEmployeeById(id)
        );

        return "edit-employee";
    }

    // UPDATE - Save edited details
    @PostMapping("/employees/{id}/update")
    public String updateEmployee(
            @PathVariable Long id,
            @Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (employee.getEmail() != null
                && service.emailExistsForAnother(
                    employee.getEmail(), id)) {

            result.rejectValue(
                "email",
                "duplicate",
                "This email is already registered"
            );
        }

        if (result.hasErrors()) {
            employee.setId(id);
            return "edit-employee";
        }

        service.updateEmployee(id, employee);

        redirectAttributes.addFlashAttribute(
            "success", "Employee updated successfully!"
        );

        return "redirect:/employees";
    }

    // DELETE - Remove employee
    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        service.deleteEmployee(id);

        redirectAttributes.addFlashAttribute(
            "success", "Employee deleted successfully!"
        );

        return "redirect:/employees";
    }
}
