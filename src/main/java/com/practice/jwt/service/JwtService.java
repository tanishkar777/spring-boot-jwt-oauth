package com.practice.jwt.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.practice.jwt.model.Product;

import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	List<Product> products = null;

	@PostConstruct
	public void saveAllProducts() {

		products = IntStream.rangeClosed(1, 100)
				.mapToObj(x -> new Product(x, "Product-" + x, new Random().nextInt(10), new Random().nextInt(5000)))
				.collect(Collectors.toList());
	}

	public List<Product> getAllProducts() {
		return products;
	}

	public Product getProductByID(int id) throws Exception {

		return products.stream().filter(product -> Integer.valueOf(product.getId()).equals(Integer.valueOf(id)))
				.findAny().orElseThrow(() -> new Exception("Product Not found withe the given Id : " + id));
	}
}
