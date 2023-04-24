package com.rodrigues.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rodrigues.ecommerce.entity.Seller;
import com.rodrigues.ecommerce.repository.SellerRepository;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SellerService {

	private SellerRepository sellerRepository;

	public List<Seller> getAllSellers() {
		return sellerRepository.findAll();
	}

	public Seller getSellerById(Long sellerId) {
		Optional<Seller> optional = sellerRepository.findById(sellerId);
		return optional.orElseThrow(() -> new ResourceNotFoundException("Seller not found for id " + sellerId));
	}

	public Long createSeller(Seller seller) {
		seller = sellerRepository.save(seller);
		return seller.getSellerId();
	}
}