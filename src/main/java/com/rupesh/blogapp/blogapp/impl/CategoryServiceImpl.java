package com.rupesh.blogapp.blogapp.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rupesh.blogapp.blogapp.dto.CategoryDto;
import com.rupesh.blogapp.blogapp.entities.CategoryEntity;
import com.rupesh.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.rupesh.blogapp.blogapp.repositories.CategoryRepository;
import com.rupesh.blogapp.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		CategoryEntity categoryEntity = this.modelMapper.map(categoryDto, CategoryEntity.class);
		
		CategoryEntity addedCategory = this.categoryRepository.saveAndFlush(categoryEntity);
		
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		categoryEntity.setCategoryTitle(categoryDto.getCategoryTitle());
		categoryEntity.setCategoryDescription(categoryDto.getCategoryDescription());
		
		CategoryEntity updatedCategory = this.categoryRepository.saveAndFlush(categoryEntity);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {

		CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		this.categoryRepository.delete(categoryEntity);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		
		CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		return this.modelMapper.map(categoryEntity, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		
		List<CategoryEntity> categoryEntities = this.categoryRepository.findAll();
		
		List<CategoryDto> categoryDtos = categoryEntities.stream().map((categoryEntity) -> this.modelMapper.map(categoryEntity, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}

}
