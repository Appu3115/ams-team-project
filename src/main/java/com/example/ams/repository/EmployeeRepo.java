package com.example.ams.repository;

import com.example.ams.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Employees, Integer> {

}
