package com.tmkcomputers.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tmkcomputers.springjwt.models.Note;


@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

	Optional<Note> findById(Long noteId);

}