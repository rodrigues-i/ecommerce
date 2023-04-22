package com.rodrigues.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigues.ecommerce.entity.Customer;
import com.rodrigues.ecommerce.service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		return ResponseEntity.ok().body(customers);
	}

	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		if (customer == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(customer);
	}

	@PostMapping
	public ResponseEntity<Long> createCustomer(@RequestBody Customer customer) {
		Long id = customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}

	@PutMapping("{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long customerId,
			@RequestBody Customer customer) {
		Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
		if (updatedCustomer == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(updatedCustomer);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long customerId) {
		customerService.deleteCustomer(customerId);
		return ResponseEntity.noContent().build();
	}

}