package com.rodrigues.ecommerce.controlller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.rodrigues.ecommerce.controller.SellerController;
import com.rodrigues.ecommerce.entity.Seller;
import com.rodrigues.ecommerce.service.SellerService;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

@WebMvcTest(SellerController.class)
public class SellerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private SellerService sellerService;
	private Seller seller;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		seller = new Seller(1L, "John", "Doe", "johndoe@example.com", "pass");
	}
	
	@Test
	public void getAllSellersShouldReturnSellers() throws Exception {
		// given
		Seller seller2 = new Seller(2L, "Douglas", "Souza", "douglas@email.com", "mystrongpass");
		List<Seller> sellers = Arrays.asList(seller, seller2);
		String jsonString = objectMapper.writeValueAsString(sellers);
		
		when(sellerService.getAllSellers()).thenReturn(sellers);
		
		// when
		mockMvc.perform(get("/sellers")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().json(jsonString))
		.andExpect(jsonPath("$[0].firstName").value(seller.getFirstName()))
		.andExpect(jsonPath("$[0].email").value(seller.getEmail()))
		.andExpect(jsonPath("$[1].firstName").value(seller2.getFirstName()));
		
		verify(sellerService).getAllSellers();
	}
	
	@Test
	public void getSellerByIdShouldReturnSeller() throws Exception {
		when(sellerService.getSellerById(seller.getSellerId())).thenReturn(seller);
		
		mockMvc.perform(get("/sellers/{id}", seller.getSellerId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.firstName").value(seller.getFirstName()));
		
		verify(sellerService).getSellerById(seller.getSellerId());
	}
	
	@Test
	public void getSellerByIdShouldReturnStausNotFound() throws Exception {
		when(sellerService.getSellerById(3L)).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/sellers/{id}", 3L).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
		
		verify(sellerService).getSellerById(3L);
	}
}
