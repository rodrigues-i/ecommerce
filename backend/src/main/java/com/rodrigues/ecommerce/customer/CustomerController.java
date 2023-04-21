package com.rodrigues.ecommerce.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping
	public ResponseEntity<Long> createCustomer(@RequestBody Customer customer) {
		Long id = customerService.createCustomer(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(id);
	}
}