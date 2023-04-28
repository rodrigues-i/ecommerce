package com.rodrigues.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigues.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}