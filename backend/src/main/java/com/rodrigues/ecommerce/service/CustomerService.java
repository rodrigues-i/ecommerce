package com.rodrigues.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rodrigues.ecommerce.entity.Customer;
import com.rodrigues.ecommerce.repository.CustomerRepository;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CustomerService {

	private CustomerRepository customerRepository;

	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		return customers;
	}

	public Customer getCustomerById(Long customerId) {
		Optional<Customer> obj = customerRepository.findById(customerId);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Customer not found for id " + customerId));
	}

	public Long createCustomer(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		return savedCustomer.getCustomerId();
	}

	public Customer updateCustomer(Long customerId, Customer customer) {
		if (customerId != customer.getCustomerId())
			return null;

		Optional<Customer> obj = customerRepository.findById(customerId);

		Customer databaseCustomer = obj
				.orElseThrow(() -> new ResourceNotFoundException("Customer not found for id " + customerId));
		if (!customer.getFirstName().trim().equals("")) {
			databaseCustomer.setFirstName(customer.getFirstName());
		}

		if (!customer.getFamilyName().trim().equals("")) {
			databaseCustomer.setFamilyName(customer.getFamilyName());
		}

		if (!customer.getEmail().trim().equals("")) {
			databaseCustomer.setEmail(customer.getEmail());
		}

		if (!customer.getPassword().trim().equals("")) {
			databaseCustomer.setPassword(customer.getPassword());
		}

		databaseCustomer = customerRepository.save(databaseCustomer);
		return databaseCustomer;
	}

	public void deleteCustomer(Long customerId) {
		Optional<Customer> obj = customerRepository.findById(customerId);
		if (obj.isEmpty())
			throw new ResourceNotFoundException("Customer not found for id " + customerId);

		customerRepository.deleteById(customerId);
	}
}