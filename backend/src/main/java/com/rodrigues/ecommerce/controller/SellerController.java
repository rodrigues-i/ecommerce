package com.rodrigues.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigues.ecommerce.entity.Seller;
import com.rodrigues.ecommerce.service.SellerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/sellers")
public class SellerController {

	private SellerService sellerService;

	@GetMapping
	public ResponseEntity<List<Seller>> getAllSellers() {
		List<Seller> sellers = sellerService.getAllSellers();
		return ResponseEntity.ok().body(sellers);
	}

	@GetMapping("{id}")
	public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long sellerId) {
		Seller seller = sellerService.getSellerById(sellerId);
		return ResponseEntity.ok().body(seller);
	}

	@PostMapping
	public ResponseEntity<Long> createSeller(@Valid @RequestBody Seller seller) {
		Long sellerId = sellerService.createSeller(seller);
		return ResponseEntity.status(HttpStatus.CREATED).body(sellerId);
	}

	@PutMapping("{id}")
	public ResponseEntity<Seller> updateSeller(@PathVariable("id") Long sellerId, @Valid @RequestBody Seller seller) {
		seller = sellerService.updateSeller(sellerId, seller);
		if (seller == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		return ResponseEntity.ok().body(seller);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteSeller(@PathVariable("id") Long sellerId) {
		sellerService.deleteSeller(sellerId);
		return ResponseEntity.noContent().build();
	}
}