package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeLogin;
import com.example.demo.entity.LeaveRequest;
import com.example.demo.repository.EmployeeLoginRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.LeaveRequestRepository;

@Service
public class EmployeeServiceImpl 
        implements EmployeeService {

	@Autowired
	private LeaveRequestRepository leaveRequestRepository;
	
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeLoginRepository employeeLoginRepository;
    
    

    @Override
    public Employee saveEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {

        Employee existingEmployee = employeeRepository
                .findById(id)
                .orElse(null);

        if(existingEmployee != null) {

            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setDateOfBirth(employee.getDateOfBirth());
            existingEmployee.setGender(employee.getGender());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setAlternatePhone(employee.getAlternatePhone());
            existingEmployee.setAddress(employee.getAddress());
            existingEmployee.setCity(employee.getCity());
            existingEmployee.setState(employee.getState());
            existingEmployee.setPincode(employee.getPincode());
            existingEmployee.setPhoto(employee.getPhoto());
            existingEmployee.setJoiningDate(employee.getJoiningDate());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setDesignation(employee.getDesignation());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setManagerName(employee.getManagerName());
            existingEmployee.setEmploymentType(employee.getEmploymentType());
            existingEmployee.setApproved(employee.isApproved());

            return employeeRepository.save(existingEmployee);
        }

        return null;
    }

    @Override
    public void deleteEmployee(Long id) {

        employeeRepository.deleteById(id);
    }
    
    
    
    
    @Override
    public void approveEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id).orElse(null);

        if(employee != null) {

            employee.setApproved(true);

            employeeRepository.save(employee);
        }
    }
    
    
    
    @Override
    public void rejectEmployee(Long id) {

        employeeRepository.deleteById(id);
    }
    
    @Override
    public void registerEmployeeLogin(
            EmployeeLogin employeeLogin) {

        employeeLoginRepository.save(employeeLogin);
    }

   
	
	@Override
	public EmployeeLogin validateEmployeeLogin(
	        String username,
	        String password) {

	    return employeeLoginRepository
	            .findByUsernameAndPassword(
	                    username,
	                    password);
	}
    
	
	@Override
	public void approveEmployeeLogin(Long loginId) {

	    EmployeeLogin employeeLogin =
	            employeeLoginRepository
	            .findById(loginId)
	            .orElse(null);

	    if(employeeLogin != null) {

	        employeeLogin.setApproved(true);

	        employeeLoginRepository.save(employeeLogin);

	        System.out.println("Approved");
	    }
	}
	
	@Override
	public List<EmployeeLogin> getPendingEmployees() {

	    return employeeLoginRepository
	            .findByApproved(false);
	}
	
	@Override
	public void applyLeave(
	        LeaveRequest leaveRequest) {

	    leaveRequestRepository.save(leaveRequest);
	}
	
	
	@Override
	public List<LeaveRequest> getAllLeaves() {

	    return leaveRequestRepository.findAll();
	}
	
	
	@Override
	public LeaveRequest getLeaveById(Long id) {

	    return leaveRequestRepository
	            .findById(id)
	            .orElse(null);
	}
	
	
	@Override
	public List<Employee> searchEmployees(
	        String keyword) {

	    return employeeRepository
	            .findByFirstNameContaining(keyword);
	}


	@Override
	public List<Employee> filterByDepartment(
	        String department) {

	    return employeeRepository
	            .findByDepartment(department);
	}
   
}
