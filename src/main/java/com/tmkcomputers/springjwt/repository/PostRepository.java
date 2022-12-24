package com.tmkcomputers.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmkcomputers.springjwt.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
