package com.rodrigues.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping
	public ResponseEntity<Long> createSeller(@RequestBody Seller seller) {
		Long sellerId = sellerService.createSeller(seller);
		return ResponseEntity.status(HttpStatus.CREATED).body(sellerId);
	}
}