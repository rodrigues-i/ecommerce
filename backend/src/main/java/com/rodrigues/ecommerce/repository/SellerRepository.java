package com.rodrigues.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigues.ecommerce.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

}