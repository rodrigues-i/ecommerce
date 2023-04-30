package com.rodrigues.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rodrigues.ecommerce.entity.Novel;
import com.rodrigues.ecommerce.repository.NovelRepository;
import com.rodrigues.ecommerce.service.exceptions.ResourceNotFoundException;

import lombok.var;

@ExtendWith(MockitoExtension.class)
public class NovelServiceTest {

	@Mock
	private NovelRepository novelRepository;
	private NovelService underTest;
	private Novel novel;
	private Long novelId;
	private Optional<Novel> optional;

	@BeforeEach
	void setUp() {
		underTest = new NovelService(novelRepository);
		novelId = 1L;
		novel = new Novel(novelId, "Dungeon ni Deai", "Aventuras na cidade labirinto Orario e na masmorra");
		optional = Optional.of(novel);
	}

	@Test
	public void getAllNovelsShouldCallFindAllFromRepository() {
		underTest.getAllNovels();
		verify(novelRepository).findAll();
	}

	@Test
	public void getNovelByIdShouldReturnNovel() {
		given(novelRepository.findById(novelId)).willReturn(optional);

		// when
		var result = underTest.getNovelById(novelId);

		// then
		assertThat(result).isEqualTo(optional.get());
		verify(novelRepository).findById(novelId);
	}

	@Test
	public void getNovelByIdShouldThrowResourceNotFoundException() {
		given(novelRepository.findById(novelId)).willReturn(Optional.empty());

		assertThatThrownBy(() -> underTest.getNovelById(novelId))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessageContaining("Novel not found for id " + novelId);
		verify(novelRepository).findById(novelId);
	}
}