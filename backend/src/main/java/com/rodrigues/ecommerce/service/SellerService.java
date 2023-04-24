package com.rodrigues.ecommerce.service;

import org.springframework.stereotype.Service;

import com.rodrigues.ecommerce.entity.Seller;
import com.rodrigues.ecommerce.repository.SellerRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SellerService {

	private SellerRepository sellerRepository;

	public Long createSeller(Seller seller) {
		seller = sellerRepository.save(seller);
		return seller.getSellerId();
	}
}