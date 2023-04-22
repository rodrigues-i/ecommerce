package com.rodrigues.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rodrigues.ecommerce.entity.Customer;
import com.rodrigues.ecommerce.repository.CustomerRepository;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

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
	
	@Test
	void canGetCustomerById() {
		// given
		Long customerId = 1L;
		Customer customer = new Customer(customerId, "Pedro", "Pereira", "pedro@email.com", "senha");
		Optional<Customer> obj = Optional.of(customer);
		
		given(customerRepository.findById(customerId))
			.willReturn(obj);
		
		// when
		underTest.getCustomerById(customerId);
		
		// then
		verify(customerRepository).findById(customerId);
	}
	
	@Test
	void canThrowResourceNotFoundExceptionWhenCustomerDoesNotExist() {
		// given
		Long customerId = 1L;
		given(customerRepository.findById(customerId))
			.willReturn(Optional.empty());
		
		// when		
		// then
		assertThatThrownBy(() -> underTest.getCustomerById(customerId))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessageContaining("Customer not found for id " + customerId);
	}

}