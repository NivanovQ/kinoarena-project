package com.example.kino.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.example.kino.model.Projection;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserReseravtionDTO extends ProjectionsDTO{
	
	private Integer priceOfEachTicket;
	private Set<String> numberOfSeats;
	
	public UserReseravtionDTO(Long id,String name, LocalDate date, LocalTime time, int hall, String description, String genre,
			boolean isPrimiery, String durationInMinutes, int priceOfEachTicket, Set<String>numberOfSeat) {
		super(id, name,date, time, hall, description, genre, isPrimiery, durationInMinutes);
		this.numberOfSeats = new HashSet<String>(numberOfSeat);
		this.priceOfEachTicket = priceOfEachTicket;
	}
	
	
	

}
