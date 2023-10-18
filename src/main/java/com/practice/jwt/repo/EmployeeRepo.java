package com.practice.jwt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.jwt.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	public Optional<Employee> findByName(String name);

}
