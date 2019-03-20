package com.example.kino.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	
	@OneToOne
	private Projection projection;
	
	
	
	
	@ManyToOne
	private User user;
	
	
}
