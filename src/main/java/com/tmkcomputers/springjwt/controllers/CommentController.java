package com.tmkcomputers.springjwt.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tmkcomputers.springjwt.exception.ResourceNotFoundException;
import com.tmkcomputers.springjwt.models.Comment;
import com.tmkcomputers.springjwt.repository.CommentRepository;
import com.tmkcomputers.springjwt.repository.PostRepository;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@GetMapping("/posts/{postId}/comments")
	public Page<Comment> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable) {
		return commentRepository.findByPostId(postId, pageable);
	}

	@PostMapping("/posts/{postId}/comments")
	public Comment createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody Comment comment) {
		return postRepository.findById(postId).map(post -> {
			comment.setPost(post);
			return commentRepository.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public Comment updateComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId, @Valid @RequestBody Comment commentRequest) {
		if (!postRepository.existsById(postId)) {
			throw new ResourceNotFoundException("Post", "id", postId);
		}

		return commentRepository.findById(commentId).map(comment -> {
			comment.setText(commentRequest.getText());
			return commentRepository.save(comment);
		}).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
			@PathVariable(value = "commentId") Long commentId) {
		if (!postRepository.existsById(postId)) {
			throw new ResourceNotFoundException("Post", "id", postId);
		}

		return commentRepository.findById(commentId).map(comment -> {
			commentRepository.delete(comment);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
	}
}
