package com.tmkcomputers.springjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.springjwt.exception.ResourceNotFoundException;
import com.tmkcomputers.springjwt.models.Post;
import com.tmkcomputers.springjwt.repository.PostRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	PostRepository postRepository;

	@GetMapping("/posts")
	public Page<Post> getAllPosts(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	// Get a Single Post
	@GetMapping("/posts/{id}")
	public Post getPostById(@PathVariable(value = "id") Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
	}

	@PostMapping("/posts")
	public Post createPost(@Valid @RequestBody Post post) {
		return postRepository.save(post);
	}

	@PutMapping("/posts/{postId}")
	public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
		return postRepository.findById(postId).map(post -> {
			post.setTitle(postRequest.getTitle());
			post.setDescription(postRequest.getDescription());
			post.setContent(postRequest.getContent());
			return postRepository.save(post);
		}).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
	}

	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Long postId) {
		return postRepository.findById(postId).map(post -> {
			postRepository.delete(post);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
	}

}
