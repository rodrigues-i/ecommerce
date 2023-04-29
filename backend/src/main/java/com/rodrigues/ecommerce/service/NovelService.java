package com.rodrigues.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rodrigues.ecommerce.entity.Novel;
import com.rodrigues.ecommerce.repository.NovelRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NovelService {
	private NovelRepository novelRepository;

	public List<Novel> getAllNovels() {
		return novelRepository.findAll();
	}

	public Long createNovel(Novel novel) {
		novel = novelRepository.save(novel);
		return novel.getNovelId();
	}
}