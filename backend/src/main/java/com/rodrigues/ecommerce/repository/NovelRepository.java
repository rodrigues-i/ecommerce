package com.rodrigues.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigues.ecommerce.entity.Novel;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {

}