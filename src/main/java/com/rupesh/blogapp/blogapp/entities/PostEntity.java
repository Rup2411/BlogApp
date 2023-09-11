package com.rupesh.blogapp.blogapp.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postId;
	
	@Column(name = "post_title", nullable = false, length = 100)
	private String postTitle;
	
	@Column(name = "post_content", nullable = false, length = 1000)
	private String postContent;
	
	@Column(name = "imageName", nullable = true, length = 20)
	private String imageName;
	
	@Column(name = "post_date", nullable = true)
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity categoryEntity;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	@OneToMany(mappedBy = "postEntity", cascade = CascadeType.ALL)
	private Set<CommentEntity> comments = new HashSet<>();
}
