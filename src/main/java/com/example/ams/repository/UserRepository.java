package com.example.ams.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ams.model.Employees;


@Repository
public interface UserRepository extends JpaRepository<Employees, Integer>{

	boolean existsByEmployeeId(String empId);

	boolean existsByEmail(String email);

	Optional<Employees> findByEmail(String email);

}
