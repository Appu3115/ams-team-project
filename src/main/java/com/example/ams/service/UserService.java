package com.example.ams.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ams.dto.LoginRequest;
import com.example.ams.dto.RegisterDTO;
import com.example.ams.model.Employees;
import com.example.ams.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository userrepo;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	
	 @Transactional
	    public ResponseEntity<?> createEmployee(RegisterDTO request) {
	        if (request == null) {
	            return ResponseEntity.badRequest().body("Request body is missing");
	        }

	        String empId = trimOrNull(request.getEmployeeId());
	        if (empId == null || empId.isEmpty()) {
	            return ResponseEntity.badRequest().body("employeeId is required");
	        }
	        if (userrepo.existsByEmployeeId(empId)) {
	            return ResponseEntity.badRequest().body("employeeId already exists");
	        }

	        String firstName = trimOrNull(request.getFirstName());
	        if (firstName == null || firstName.isEmpty()) {
	            return ResponseEntity.badRequest().body("firstName is required");
	        }
	        if (firstName.length() > 100) {
	            return ResponseEntity.badRequest().body("firstName must be <= 100 characters");
	        }

	        String lastName = trimOrNull(request.getLastName());
	        if (lastName == null || lastName.isEmpty()) {
	            return ResponseEntity.badRequest().body("lastName is required");
	        }
	        if (lastName.length() > 100) {
	            return ResponseEntity.badRequest().body("lastName must be <= 100 characters");
	        }

	        String email = trimOrNull(request.getEmail());
	        if (email == null || email.isEmpty()) {
	            return ResponseEntity.badRequest().body("email is required");
	        }
	        if (!isValidEmail(request.getEmail())) {
	            return ResponseEntity.badRequest().body("Invalid Email");
	        }
	        if (userrepo.existsByEmail(email)) {
	            return ResponseEntity.badRequest().body("Email already exists");
	        }

	        if (!request.getPassword().equals(request.getConfirmPassword())) {
	            return ResponseEntity.badRequest().body("Passwords do not match");
	        }
	        
	        String phone = String.valueOf(request.getPhone());
	        if (phone.length() <= 0) {
	            return ResponseEntity.badRequest().body("phone must be a positive number");
	        }
	        String phoneStr = String.valueOf(phone);
	        if (phoneStr.length() != 10 || !phoneStr.matches("\\d{10}")) {
	            return ResponseEntity.badRequest().body("phone must be 10 digits");
	        }
//	        if (!phoneStr.matches("\\d+")) {
//	            return ResponseEntity.badRequest().body("phone must contain digits only");
//	        }

	        int dept = request.getDepartmentId();
	        if (dept <= 0) {
	            return ResponseEntity.badRequest().body("departmentId must be a positive integer");
	        }

	        String role = trimOrNull(request.getRole());
	        if (role == null || role.isEmpty()) {
	            return ResponseEntity.badRequest().body("role is required");
	        }
	        if (role.length() > 100) {
	            return ResponseEntity.badRequest().body("role must be <= 100 characters");
	        }

	        Employees emp = new Employees();
	        emp.setEmployeeId(empId);
	        emp.setFirstName(firstName);
	        emp.setLastName(lastName);
	        emp.setDepartmentId(dept);
	        emp.setEmail(email);
	        emp.setPassword(passwordEncoder.encode(request.getPassword()));
	        emp.setActive(true);
	        emp.setPhone(phone);
	        emp.setRole(role);
	        userrepo.save(emp);
	        return ResponseEntity.status(HttpStatus.CREATED).body(emp);
	    }
	 
	 public ResponseEntity<?> loginEmployee(LoginRequest request){
		 Optional<Employees> optEmp = userrepo.findByEmail(request.getEmail());
		 
		 if (!isValidEmail(request.getEmail())) {
	            return ResponseEntity.badRequest().body("Invalid Email");
	        }
		 if(optEmp.isEmpty()) {
			 return ResponseEntity.badRequest().body("User Not Found");
		 }
		 
		 Employees emp = optEmp.get();
		 
		 if(!emp.getEmployeeId().equals(request.getEmployeeId())) {
			 return ResponseEntity.badRequest().body("Incorrect EmployeeId");
		 }
		 
		 if (!passwordEncoder.matches(request.getPassword(), emp.getPassword())) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
		 
		 Map<String,Object> response = new HashMap<>();
		 response.put("employeeid", request.getEmployeeId());
		 response.put("email", request.getEmail());
		 response.put("firstName",emp.getFirstName());
		 response.put("lastName",emp.getLastName());
		 response.put("role", emp.getRole());
		 response.put("message", "Login Successful");
		 return ResponseEntity.ok(response);
	 }
	 
	 
	 private boolean isValidEmail(String email) {
	        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	        return email != null && email.matches(emailRegex);
	    }
	 
	 private String trimOrNull(String s) {
	        return s == null ? null : s.trim();
	    }
}
