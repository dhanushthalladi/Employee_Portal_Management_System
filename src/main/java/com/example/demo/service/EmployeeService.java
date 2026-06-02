package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeLogin;
import com.example.demo.entity.LeaveRequest;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    List<EmployeeLogin> getPendingEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    void approveEmployee(Long id);

    void rejectEmployee(Long id);

    void registerEmployeeLogin(
            EmployeeLogin employeeLogin);

    EmployeeLogin validateEmployeeLogin(
            String username,
            String password);

    void approveEmployeeLogin(Long employeeId);
    
    void applyLeave(LeaveRequest leaveRequest);

    List<LeaveRequest> getAllLeaves();

    LeaveRequest getLeaveById(Long id);
    
    
    List<Employee> searchEmployees(String keyword);

    List<Employee> filterByDepartment(
            String department);
}