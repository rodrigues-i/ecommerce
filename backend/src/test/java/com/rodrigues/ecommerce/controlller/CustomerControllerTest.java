package com.rodrigues.ecommerce.controlller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigues.ecommerce.controller.CustomerController;
import com.rodrigues.ecommerce.entity.Customer;
import com.rodrigues.ecommerce.service.CustomerService;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CustomerService customerService;
	private Customer customer;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		customer = new Customer(1L, "John", "Doe", "johndoe@example.com", "pass");
	}

	@Test
	void getAllCustomersShouldReturnCustomers() throws Exception {
		Customer customer1 = new Customer(1L, "Nome1", "Sobrenome1", "meu@email.com", "pass");
		Customer customer2 = new Customer(2L, "Nome2", "Sobrenome2", "o.email@email.com", "senha");
		String jsonString = "[{\"customerId\":1,\"firstName\":\"Nome1\",\"familyName\":\"Sobrenome1\",\"email\":\"meu@email.com\",\"password\":\"pass\"},{\"customerId\":2,\"firstName\":\"Nome2\",\"familyName\":\"Sobrenome2\",\"email\":\"o.email@email.com\",\"password\":\"senha\"}]";

		List<Customer> customers = Arrays.asList(customer1, customer2);
		when(customerService.getAllCustomers()).thenReturn(customers);

		mockMvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(jsonString))
				.andExpect(jsonPath("$[0].customerId").value(customer1.getCustomerId()))
				.andExpect(jsonPath("$[0].firstName").value(customer1.getFirstName()))
				.andExpect(jsonPath("$[0].familyName").value(customer1.getFamilyName()))
				.andExpect(jsonPath("$[1].customerId").value(customer2.getCustomerId()))
				.andExpect(jsonPath("$[1].firstName").value(customer2.getFirstName()))
				.andExpect(jsonPath("$[1].familyName").value(customer2.getFamilyName()));

		verify(customerService).getAllCustomers();
	}

	@Test
	void getCustomerByIdShouldReturnCustomer() throws Exception {
		when(customerService.getCustomerById(1L)).thenReturn(customer);

		mockMvc.perform(get("/customers/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.customerId").value(customer.getCustomerId()))
				.andExpect(jsonPath("$.firstName").value(customer.getFirstName()))
				.andExpect(jsonPath("$.familyName").value(customer.getFamilyName()))
				.andExpect(jsonPath("$.email").value(customer.getEmail()));

		verify(customerService).getCustomerById(customer.getCustomerId());
	}

	@Test
	void whenGetCustomerByIdWithInvalidIdThenThrowsResourceNotFoundException() throws Exception {
		Long customerId = 1L;
		when(customerService.getCustomerById(customerId)).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get("/customers/{id}", customerId)).andExpect(status().isNotFound());

		verify(customerService).getCustomerById(customerId);
	}
	
	@Test
	void createCustomerShouldReturnId() throws Exception {
		when(customerService.createCustomer(customer)).thenReturn(customer.getCustomerId());
		String jsonBody = objectMapper.writeValueAsString(customer);
		
		mockMvc.perform(post("/customers")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(content().string(String.valueOf(customer.getCustomerId())));
			
	}
	
	@Test
	void updateCustomerShouldUpdateTheCustomer() throws Exception {
		String newName = "Novo";
		customer.setFirstName(newName);
		String jsonBody = objectMapper.writeValueAsString(customer);
		when(customerService.updateCustomer(customer.getCustomerId(), customer)).thenReturn(customer);
		
		mockMvc.perform(put("/customers/{id}", customer.getCustomerId())
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.customerId").exists())
			.andExpect(jsonPath("$.firstName").value(newName))
			.andExpect(jsonPath("$.familyName").exists());
	}
	
	@Test
	void updateCustomerShouldReturnBadRequestWhenIdFromCustomerIsDifferentFromIdOfUri() throws Exception {
		Long differentId = 5L;
		String jsonBody = objectMapper.writeValueAsString(customer);
		when(customerService.updateCustomer(differentId, customer)).thenReturn(null);
		
		mockMvc.perform(put("/customers/{id}", differentId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void deleteCustomerShouldRemoveCustomer() throws Exception {
		doNothing().when(customerService).deleteCustomer(customer.getCustomerId());
		
		mockMvc.perform(delete("/customers/{id}", customer.getCustomerId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	void deleteCustomerShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception {
		Long unExistingId = 3L;
		doThrow(ResourceNotFoundException.class).when(customerService).deleteCustomer(unExistingId);
		
		mockMvc.perform(delete("/customers/{id}", unExistingId))
		.andExpect(status().isNotFound());
	}
}
