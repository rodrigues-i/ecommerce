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

	public Novel updateNovel(Long novelId, Novel novel) {
		if (novelId != novel.getNovelId())
			return null;

		Optional<Novel> optional = novelRepository.findById(novelId);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("Novel not found for id " + novelId);

		Novel databaseNovel = optional.get();
		databaseNovel.setName(novel.getName().trim());
		databaseNovel.setDescription(novel.getDescription().trim());
		novel = novelRepository.save(databaseNovel);

		return novel;
	}

	public void removeNovel(Long novelId) {
		Optional<Novel> optional = novelRepository.findById(novelId);
		if (optional.isEmpty())
			throw new ResourceNotFoundException("Novel not found for id " + novelId);

		novelRepository.deleteById(novelId);
	}
}