package com.example.kino.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kino.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
	
	
	
}
