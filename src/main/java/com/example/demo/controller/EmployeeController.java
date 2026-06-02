package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    
    // Save Employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee) {

        employeeService.saveEmployee(employee);

        return "redirect:/employees/all";
    }

    
    // Get All Employees
    @GetMapping("/all")
    public String getAllEmployees(Model model) {

        List<Employee> employees = employeeService.getAllEmployees();

        model.addAttribute("employees", employees);

        return "employee-list";
    }

    
    // Get Employee By Id
    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id,
                                  Model model) {

        Employee employee = employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "employee-profile";
    }

    
    // Update Employee
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @ModelAttribute Employee employee) {

        employeeService.updateEmployee(id, employee);

        return "redirect:/employees/all";
    }

    
    // Delete Employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "redirect:/employees/all";
    }
    
    @GetMapping("/admin/login")
    public String adminLogin() {

        return "admin-login";
    }
    
    

        @GetMapping("/employee/login")
        public String employeeLogin() {

            return "employee-login";
        }

        @PostMapping("/employee/login")
        public String employeeLoginProcess() {

            return "employee-dashboard";
        }
    
        
        @GetMapping("/employee-register")
        public String registerPage(Model model)
        {
            model.addAttribute("employee", new Employee());
            return "employee-register";
        }

       
}
