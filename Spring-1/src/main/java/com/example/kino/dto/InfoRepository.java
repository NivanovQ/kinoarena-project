package com.example.kino.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kino.model.Info;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
	
	
	public Info findByDescription(String description);
}
