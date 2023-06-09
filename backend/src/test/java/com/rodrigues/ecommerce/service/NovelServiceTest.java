package com.rodrigues.ecommerce.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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
	
	@Test
	public void createNovelShouldReturnIdOfCreatedNovel() {
		given(novelRepository.save(novel)).willReturn(novel);
		
		// when
		var result = underTest.createNovel(novel);
		
		// then
		assertThat(result).isEqualTo(novel.getNovelId());
		verify(novelRepository).save(novel);
	}
	
	@Test
	public void updateNovelShouldReturnUpdatedNovel() {
		// given
		String newName = "Sword art Online";
		novel.setName(newName);
		given(novelRepository.findById(novelId)).willReturn(optional);
		given(novelRepository.save(novel)).willReturn(novel);
		
		// when
		var result = underTest.updateNovel(novelId, novel);
		
		// then
		assertThat(result.getName()).isEqualTo(newName);
		verify(novelRepository).save(novel);
	}
	
	@Test
	public void updateNovelShouldReturnNullWhenIddifferentFromNovel() {
		Long differentId = 3L;
		
		// when
		var result = underTest.updateNovel(differentId, novel);
		
		// then
		assertThat(result).isNull();
		verify(novelRepository, never()).findById(differentId);
		verify(novelRepository, never()).save(novel);
	}
	
	@Test
	public void updateNovelShouldThrowResourceNotFoundExceptionWhenNovelDoesNotExist() {
		given(novelRepository.findById(novelId)).willReturn(Optional.empty());
		
		// when
		// then
		assertThatThrownBy(() -> underTest.updateNovel(novelId, novel))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessageContaining("Novel not found for id " + novelId);
	}
	
	@Test
	public void deleteNovelShouldRemoveNovel() {
		given(novelRepository.findById(novelId)).willReturn(optional);
		doNothing().when(novelRepository).deleteById(novelId);
		
		// when
		underTest.removeNovel(novelId);
		
		verify(novelRepository).findById(novelId);
		verify(novelRepository).deleteById(novelId);
	}
	
	@Test
	public void deleteNovelShouldThrowResourceNotFoundExceptionWhenNovelDoesNotExist() {
		given(novelRepository.findById(novelId)).willReturn(Optional.empty());
		
		assertThatThrownBy(() -> underTest.removeNovel(novelId))
			.isInstanceOf(ResourceNotFoundException.class)
			.hasMessageContaining("Novel not found for id " + novelId);
		
		verify(novelRepository, never()).deleteById(novelId);
	}
}