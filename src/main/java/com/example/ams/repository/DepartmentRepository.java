package com.example.ams.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ams.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{

}
