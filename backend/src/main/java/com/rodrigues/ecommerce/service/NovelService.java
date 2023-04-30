package com.rodrigues.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rodrigues.ecommerce.entity.Novel;
import com.rodrigues.ecommerce.repository.NovelRepository;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NovelService {
	private NovelRepository novelRepository;

	public List<Novel> getAllNovels() {
		return novelRepository.findAll();
	}

	public Novel getNovelById(Long novelId) {
		Optional<Novel> optional = novelRepository.findById(novelId);
		return optional.orElseThrow(() -> new ResourceNotFoundException("Novel not found for id " + novelId));
	}

	public Long createNovel(Novel novel) {
		novel = novelRepository.save(novel);
		return novel.getNovelId();
	}
}