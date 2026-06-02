package com.example.demo.controller;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Announcement;
import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeLogin;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.repository.AnnouncementRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private AnnouncementRepository announcementRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    
    // Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard() {

        return "admin-dashboard";
    }


    // Open Add Employee Page
    @GetMapping("/addEmployee")
    public String addEmployeePage(Model model) {

        model.addAttribute("employee", new Employee());

        return "add-employee";
    }


    // Save Employee
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee,
                               Model model) {

        employeeRepository.save(employee);

        model.addAttribute("employeeName",
                employee.getFirstName() + " " + employee.getLastName());

        return "employee_success";
    }


    // View Employees
    @GetMapping("/viewEmployees")
    public String viewEmployees(Model model) {

        List<Employee> employees =
                employeeService.getAllEmployees();

        model.addAttribute("employees", employees);

        return "view-employees";
    }


    // Delete Employee
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return "redirect:/admin/viewEmployees";
    }


    // Open Update Page
    @GetMapping("/updateEmployee/{id}")
    public String updateEmployeePage(
            @PathVariable Long id,
            Model model) {

        Employee employee =
                employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "update-employee";
    }


    // Update Employee
    @PostMapping("/updateEmployee")
    public String updateEmployee(
            @ModelAttribute Employee employee) {

        employeeService.saveEmployee(employee);

        return "redirect:/admin/viewEmployees";
    }


    // Approval Employees
    @GetMapping("/approvalEmployees")
    public String approvalEmployees(Model model) {

        List<EmployeeLogin> employees =
                employeeService.getPendingEmployees();

        model.addAttribute("employees", employees);

        return "approval-employees";
    }


    // Approve Employee
    @GetMapping("/approve/{id}")
    public String approveEmployee(
            @PathVariable Long id) {

        employeeService.approveEmployeeLogin(id);

        return "redirect:/admin/approvalEmployees";
    }


    // Reject Employee
    @GetMapping("/reject/{id}")
    public String rejectEmployee(@PathVariable Long id) {

        employeeService.rejectEmployee(id);

        return "redirect:/admin/approvalEmployees";
    }
    
    @GetMapping("/leaves")
    public String leaveRequests(
            Model model) {

        model.addAttribute(
                "leaves",
                employeeService.getAllLeaves());

        return "leave-requests";
    }
    
    
    @GetMapping("/approveLeave/{id}")
    public String approveLeave(
            @PathVariable Long id) {

        LeaveRequest leaveRequest =
                employeeService.getLeaveById(id);

        leaveRequest.setStatus("Approved");

        employeeService.applyLeave(leaveRequest);

        return "redirect:/admin/leaves";
    }
    
    
    @GetMapping("/rejectLeave/{id}")
    public String rejectLeave(
            @PathVariable Long id) {

        LeaveRequest leaveRequest =
                employeeService.getLeaveById(id);

        leaveRequest.setStatus("Rejected");

        employeeService.applyLeave(leaveRequest);

        return "redirect:/admin/leaves";
    }
    
 // OPEN ADMIN LOGIN PAGE

    


    // CHECK ADMIN LOGIN

    @PostMapping("/login")
    public String adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        if(username.equals("Dhanush")
                && password.equals("Dhanu$h09")) {

            return "redirect:/admin/dashboard";
        }

        model.addAttribute(
                "error",
                "Wrong Username or Password");

        return "admin-login";
    }
    
    
    @GetMapping("/search")
    public String searchEmployee(
            @RequestParam String keyword,
            Model model) {

        model.addAttribute(
                "employees",
                employeeService.searchEmployees(keyword));

        return "view-employees";
    }
    
    
    @GetMapping("/announcement")
    public String announcementPage(
            Model model) {

        model.addAttribute(
                "announcement",
                new Announcement());

        return "announcement";
    }
    
    
    @PostMapping("/saveAnnouncement")
    public String saveAnnouncement(
            @ModelAttribute Announcement announcement) {

        announcement.setDate(LocalDate.now());

        announcementRepository.save(announcement);

        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/viewAnnouncements")
    public String viewAnnouncements(
            Model model) {

        model.addAttribute(
                "announcements",
                announcementRepository.findAll());

        return "view-announcements";
    }
    
    @GetMapping("/announcements")
    public String announcementsPage() {

        return "announcements";
    }
    
    
    
}
