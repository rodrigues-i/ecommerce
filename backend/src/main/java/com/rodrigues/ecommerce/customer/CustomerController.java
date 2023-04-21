package com.rodrigues.ecommerce.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Long> createCustomer(@PathVariable Customer customer) {
		Long id = customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
}