package com.rupesh.blogapp.blogapp.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rupesh.blogapp.blogapp.dto.PostDto;
import com.rupesh.blogapp.blogapp.dto.PostResponse;
import com.rupesh.blogapp.blogapp.entities.CategoryEntity;
import com.rupesh.blogapp.blogapp.entities.PostEntity;
import com.rupesh.blogapp.blogapp.entities.UserEntity;
import com.rupesh.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.rupesh.blogapp.blogapp.repositories.CategoryRepository;
import com.rupesh.blogapp.blogapp.repositories.PostRepository;
import com.rupesh.blogapp.blogapp.repositories.UserRepository;
import com.rupesh.blogapp.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));
		
		CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
		
		PostEntity postEntity = this.modelMapper.map(postDto, PostEntity.class);
		postEntity.setImageName("default.png");
		postEntity.setAddedDate(new Date());
		postEntity.setCategoryEntity(categoryEntity);
		postEntity.setUserEntity(userEntity);
		
		PostEntity newPost = this.postRepository.saveAndFlush(postEntity);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		PostEntity postEntity = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		postEntity.setPostTitle(postDto.getPostTitle());
		postEntity.setPostContent(postDto.getPostContent());
		postEntity.setImageName(postDto.getImageName());
		
		PostEntity updatedPost = this.postRepository.saveAndFlush(postEntity);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		
		PostEntity postEntity = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		this.postRepository.delete(postEntity);

	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
		
		Sort sort = null;
		
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<PostEntity> pagePost = this.postRepository.findAll(p);
		
		List<PostEntity> allPosts = pagePost.getContent();
		
		List<PostDto> postDtos = allPosts.stream().map((postEntity) -> this.modelMapper.map(postEntity, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		PostEntity postEntity = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		
		return this.modelMapper.map(postEntity, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId ) {
		
		CategoryEntity categoryEntity = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));
		
		List<PostEntity> postEntities = this.postRepository.findByCategoryEntity(categoryEntity);
		
		List<PostDto> postDtos = postEntities.stream().map((postEntity) -> this.modelMapper.map(postEntity, PostDto.class))
				.collect(Collectors.toList());
		
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		UserEntity userEntity = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		
		List<PostEntity> postEntities = this.postRepository.findByUserEntity(userEntity);
		
		List<PostDto> postDtos = postEntities.stream().map((postEntity) -> this.modelMapper.map(postEntity, PostDto.class))
				.collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<PostEntity> postEntities = this.postRepository.findByPostTitleContaining(keyword);
		
		List<PostDto> postDtos = postEntities.stream().map((postEntity) -> this.modelMapper.map(postEntity, PostDto.class))
				.collect(Collectors.toList());
		
		return postDtos;
	}

}
