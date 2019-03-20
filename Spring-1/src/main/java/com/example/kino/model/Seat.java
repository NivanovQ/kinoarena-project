package com.example.kino.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name="Seats")
public class Seat {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long seatId;
	private int numberOfPlace;
	
	@OneToOne
	private Reservation reservation;
	
	@OneToOne
	private Projection projection;
	
	
}
