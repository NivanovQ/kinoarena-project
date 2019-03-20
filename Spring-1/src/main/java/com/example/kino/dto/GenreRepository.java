package com.example.kino.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kino.model.Genre;

@Repository
public interface GenreRepository  extends JpaRepository<Genre, Long>{

	public Genre findByGenre(String genre);
}
