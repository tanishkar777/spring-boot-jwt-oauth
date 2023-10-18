
package com.practice.jwt.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.practice.jwt.model.Employee;
import com.practice.jwt.model.EmployeeDetails;
import com.practice.jwt.repo.EmployeeRepo;

@Component
public class EmployeeDetailsService implements UserDetailsService {

	private EmployeeRepo repo;

	public EmployeeDetailsService(EmployeeRepo repo) {
		this.repo = repo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // TODO Auto-generated
																								// method stub
		Optional<Employee> emp = repo.findByName(username);
		return emp.map(EmployeeDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with the given Name"));

	}

}
