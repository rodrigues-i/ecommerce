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

	public Seller updateSeller(Long sellerId, Seller seller) {
		if (sellerId != seller.getSellerId())
			return null;
		Optional<Seller> optional = sellerRepository.findById(sellerId);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("Seller not found for id " + sellerId);

		Seller databaseSeller = optional.get();
		if (!seller.getFirstName().trim().equals(databaseSeller.getFirstName())
				&& !seller.getFirstName().trim().equals(""))
			databaseSeller.setFirstName(seller.getFirstName().trim());

		if (!seller.getFamilyName().trim().equals(databaseSeller.getFamilyName())
				&& !seller.getFamilyName().trim().equals(""))
			databaseSeller.setFamilyName(seller.getFamilyName().trim());

		if (!seller.getEmail().trim().equals(databaseSeller.getEmail()) && !seller.getEmail().trim().equals(""))
			databaseSeller.setEmail(seller.getEmail().trim());

		if (!seller.getPassword().trim().equals(databaseSeller.getPassword())
				&& !seller.getPassword().trim().equals(""))
			databaseSeller.setPassword(seller.getPassword().trim());

		return sellerRepository.save(databaseSeller);
	}

	public void deleteSeller(Long sellerId) {
		Optional<Seller> optional = sellerRepository.findById(sellerId);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("Seller not found for id " + sellerId);
		sellerRepository.deleteById(sellerId);
	}
}