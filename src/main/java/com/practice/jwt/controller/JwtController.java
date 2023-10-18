package com.practice.jwt.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.jwt.model.AuthRequest;
import com.practice.jwt.model.AuthResponse;
import com.practice.jwt.model.Employee;
import com.practice.jwt.model.Product;
import com.practice.jwt.repo.EmployeeRepo;
import com.practice.jwt.service.JwtService;
import com.practice.jwt.service.JwtUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/jwt-practice")
public class JwtController {

	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private JwtService service;
	@Autowired
	private JwtUtility jwtUtility;

	@GetMapping("/welcome")
	public ResponseEntity<String> getWelcomeMessage(/* @RequestParam String name */) {
		return new ResponseEntity<String>("Welcome to Oauth2 World!!!!!!!!!!!!!!", HttpStatus.CREATED);
	}

	@PostMapping("/save")
	public ResponseEntity<String> saveEmployeeDetails(@RequestBody List<Employee> employee) throws Exception {

		List<Employee> employees = employeeRepo.saveAll(employee);

		if (null != employees) {
			return new ResponseEntity<>("Employees Saved.....", HttpStatus.CREATED);
		}

		else {
			throw new Exception("Exception Occurred while saving Employees");
		}

	}

	@GetMapping("/getEmployees")
	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> employees = employeeRepo.findAll();
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);

	}

	@GetMapping("/getAllProducts")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = service.getAllProducts();
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

	}

	@GetMapping("/getProductById/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Product> getProductById(@PathVariable int id) throws Exception {
		Product product = service.getProductByID(id);
		return new ResponseEntity<Product>(product, HttpStatus.OK);

	}

}
