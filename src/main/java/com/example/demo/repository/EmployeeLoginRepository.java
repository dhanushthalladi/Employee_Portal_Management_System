package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.EmployeeLogin;

public interface EmployeeLoginRepository
        extends JpaRepository<EmployeeLogin, Long> {

    EmployeeLogin findByUsernameAndPassword(
            String username,
            String password);

    List<EmployeeLogin> findByApproved(
            boolean approved);
    
    EmployeeLogin findByEmployeeEmployeeId(
            Long employeeId);
}