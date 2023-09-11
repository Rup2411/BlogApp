package com.rupesh.blogapp.blogapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	
	@Column(name = "comment_content", nullable = false, length = 1000)
	private String commentContent;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity userEntity;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private PostEntity postEntity;
}
