package com.rodrigues.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rodrigues.ecommerce.entity.Customer;
import com.rodrigues.ecommerce.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers;
	}
	
	public Customer getCustomerById(Long customerId) {
		Optional<Customer> obj = customerRepository.findById(customerId);
		if(obj.isEmpty()) {
			return null;
		}
		
		Customer customer = obj.get();
		return customer;
	}

	public Long createCustomer(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		return savedCustomer.getCustomerId();
	}
}