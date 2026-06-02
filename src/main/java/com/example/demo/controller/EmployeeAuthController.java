package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeLogin;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.repository.EmployeeLoginRepository;
import com.example.demo.repository.LeaveRequestRepository;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeAuthController {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
	@Autowired
	private EmployeeLoginRepository employeeLoginRepository;
	
    @Autowired
    private EmployeeService employeeService;


    


    // Employee Register Page

    


    // REGISTER PAGE

    @GetMapping("/employee/register")
    public String registerPage() {

        return "employee-register";
    }

    @GetMapping("/register")
    public String employeeRegisterPage(Model model) {

        model.addAttribute(
                "employee",
                new Employee());

        return "employee-register";
    }


    @GetMapping("/login")
    public String employeeLoginPage() {

        return "employee-login";
    }


    @PostMapping("/saveRegister")
    public String saveRegister(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String username,
            @RequestParam String password) {

        Employee employee = new Employee();

        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setStatus("Pending");

        Employee savedEmployee =
                employeeService.saveEmployee(employee);

        EmployeeLogin employeeLogin =
                new EmployeeLogin();

        employeeLogin.setUsername(username);
        employeeLogin.setPassword(password);
        employeeLogin.setApproved(false);

        employeeLogin.setEmployee(savedEmployee);

        employeeService
                .registerEmployeeLogin(employeeLogin);

        return "redirect:/employee/login";
    }

    // Employee Login Validation

    @PostMapping("/employeeLogin")
    public String employeeLogin(

            @RequestParam String username,

            @RequestParam String password,

            Model model) {


        EmployeeLogin employeeLogin =
                employeeService
                .validateEmployeeLogin(
                        username,
                        password);


        if(employeeLogin != null) {


            // Approval Check

            if(employeeLogin.isApproved()) {

                model.addAttribute(
                        "employee",
                        employeeLogin.getEmployee());

                return "employee-dashboard";
            }

            else {

                model.addAttribute(
                        "error",
                        "Waiting For Admin Approval");

                return "employee-login";
            }
        }


        model.addAttribute(
                "error",
                "Username or Password Incorrect");

        return "employee-login";
    }
    
    
    @GetMapping("/editProfile/{id}")
    public String editProfile(
            @PathVariable Long id,
            Model model) {

        Employee employee =
                employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "employee-update";
    }
    
    
    @PostMapping("/updateProfile/{id}")
    public String updateProfile(

            @PathVariable Long id,

            @ModelAttribute Employee employee,

            Model model) {

        Employee updatedEmployee =
                employeeService
                .updateEmployee(id, employee);

        model.addAttribute(
                "employee",
                updatedEmployee);

        return "employee-dashboard";
    }
    
    
    @GetMapping("/changePassword/{id}")
    public String changePasswordPage(
            @PathVariable Long id,
            Model model) {

        model.addAttribute("employeeId", id);

        return "change-password";
    }
    
    
    
    
    @PostMapping("/savePassword")
    public String savePassword(

            @RequestParam Long employeeId,

            @RequestParam String password,

            Model model) {

        EmployeeLogin employeeLogin =
                employeeLoginRepository
                .findByEmployeeEmployeeId(employeeId);

        employeeLogin.setPassword(password);

        employeeLoginRepository.save(employeeLogin);

        model.addAttribute(
                "success",
                "Password Changed Successfully");

        return "employee-login";
    }
    
    
    @GetMapping("/completeProfile/{id}")
    public String completeProfilePage(
            @PathVariable Long id,
            Model model) {

        Employee employee =
                employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "complete-profile";
    }
    
    
    @PostMapping("/saveProfile/{id}")
    public String saveProfile(

            @PathVariable Long id,

            @ModelAttribute Employee employee,

            Model model) {

        Employee updatedEmployee =
                employeeService.updateEmployee(id, employee);

        model.addAttribute(
                "employee",
                updatedEmployee);

        return "employee-dashboard";
    }
    
    @GetMapping("/applyLeave/{id}")
    public String applyLeavePage(
            @PathVariable Long id,
            Model model) {

        LeaveRequest leaveRequest =
                new LeaveRequest();

        Employee employee =
                employeeService.getEmployeeById(id);

        leaveRequest.setEmployee(employee);

        model.addAttribute(
                "leaveRequest",
                leaveRequest);

        return "apply-leave";
    }
    
    
    @PostMapping("/saveLeave")
    public String saveLeave(

            @ModelAttribute LeaveRequest leaveRequest,

            Model model) {

        leaveRequest.setStatus("Pending");

        employeeService.applyLeave(leaveRequest);

        model.addAttribute(
                "success",
                "Leave Applied Successfully");

        model.addAttribute(
                "employee",
                leaveRequest.getEmployee());

        return "employee-dashboard";
    }
    
    
 
}