package com.rodrigues.ecommerce.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rodrigues.ecommerce.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;
	private CustomerService underTest;

	@BeforeEach
	void setUp() {
		underTest = new CustomerService(customerRepository);
	}

	@Test
	void canGetAllCustomers() {
		// when
		underTest.getAllCustomers();

		// then
		verify(customerRepository).findAll();
	}

}