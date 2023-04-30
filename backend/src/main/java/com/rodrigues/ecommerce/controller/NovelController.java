package com.rodrigues.ecommerce.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigues.ecommerce.entity.Novel;
import com.rodrigues.ecommerce.service.NovelService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/novels")
@AllArgsConstructor
public class NovelController {

	private final NovelService novelService;

	@GetMapping
	public ResponseEntity<List<Novel>> getAllNovels() {
		List<Novel> novels = novelService.getAllNovels();
		return ResponseEntity.ok().body(novels);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Novel> getNovelById(@PathVariable("id") Long novelId) {
		Novel novel = novelService.getNovelById(novelId);
		return ResponseEntity.ok().body(novel);
	}

	@PostMapping
	public ResponseEntity<Long> createNovel(@Valid @RequestBody Novel novel) {
		Long createdNovelId = novelService.createNovel(novel);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdNovelId);
	}
}