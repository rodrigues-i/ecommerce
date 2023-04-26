package com.rodrigues.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rodrigues.ecommerce.entity.Seller;
import com.rodrigues.ecommerce.repository.SellerRepository;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {
	@Mock
	private SellerRepository sellerRepository;
	private SellerService underTest;
	private Seller seller;
	private Long sellerId;
	Optional<Seller> optional;

	@BeforeEach
	void setUp() {
		underTest = new SellerService(sellerRepository);
		sellerId = 1L;
		seller = new Seller(sellerId, "name", "familyName", "email@gmail.com", "password");
		optional = Optional.of(seller);
	}

	@Test
	public void getAllSellersShouldCallFindAllFromRepository() {
		underTest.getAllSellers();
		verify(sellerRepository).findAll();
	}

	@Test
	public void getSellerByIdShouldReturnSeller() {
		given(sellerRepository.findById(sellerId)).willReturn(optional);

		// when
		underTest.getSellerById(sellerId);

		// then
		verify(sellerRepository).findById(sellerId);
	}

	@Test
	public void getSellerByIdShouldThrowResourceNotFoundException() {
		given(sellerRepository.findById(sellerId)).willReturn(Optional.empty());

		// when
		// then
		assertThatThrownBy(() -> underTest.getSellerById(sellerId)).isInstanceOf(ResourceNotFoundException.class)
				.hasMessageContaining("Seller not found for id " + sellerId);
	}

	@Test
	public void createSellerShouldReturnId() {
		given(sellerRepository.save(seller)).willReturn(seller);

		// when
		Long id = underTest.createSeller(seller);

		// then
		assertThat(id).isEqualTo(seller.getSellerId());
		verify(sellerRepository).save(seller);
	}
	
	@Test
	public void updateSellerShouldReturnUpdatedSeller() {
		// given
		String newName = "newName";
		seller.setFirstName(newName);
		
		given(sellerRepository.save(seller)).willReturn(seller);
		given(sellerRepository.findById(sellerId)).willReturn(optional);
		
		// when
		Seller updatedSeller = underTest.updateSeller(sellerId, seller);
		
		// then
		assertThat(newName).isEqualTo(updatedSeller.getFirstName());
		verify(sellerRepository).findById(sellerId);
		verify(sellerRepository).save(seller);
	}
	
	@Test
	public void updateSellerShouldReturnNullWhenSellerDoesNotExist() {
		// given
		Long nonExistingId = 3L;
		
		// when
		Seller result = underTest.updateSeller(nonExistingId, seller);
		
		// then
		assertThat(result).isNull();
		verify(sellerRepository, never()).findById(nonExistingId);
		verify(sellerRepository, never()).save(seller);
	}
	
	@Test
	public void updateSellerShouldThrowResourceNotFoundExceptionWhenSellerDoesNotExist() {
		// given
		given(sellerRepository.findById(sellerId)).willReturn(Optional.empty());
		
		// when
		// then
		assertThatThrownBy(() -> underTest.updateSeller(sellerId, seller))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessageContaining("Seller not found for id " + sellerId);
		
		verify(sellerRepository).findById(sellerId);
		verify(sellerRepository, never()).save(seller);
	}
}