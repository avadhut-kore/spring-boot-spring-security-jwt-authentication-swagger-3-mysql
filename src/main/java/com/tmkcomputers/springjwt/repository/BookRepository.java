package com.tmkcomputers.springjwt.repository;

import org.springframework.data.repository.CrudRepository;

import com.tmkcomputers.springjwt.models.Book;

//repository that extends CrudRepository  
public interface BookRepository extends CrudRepository<Book, Integer> {
}