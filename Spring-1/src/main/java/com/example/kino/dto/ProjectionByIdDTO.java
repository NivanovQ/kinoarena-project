package com.example.kino.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.kino.model.Reservation;
import com.example.kino.model.Seat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ProjectionByIdDTO extends ProjectionsDTO {
	
	
	public ProjectionByIdDTO(Long id,String name, LocalDate date , LocalTime time, int hall, String description , String genre,boolean isPrimiery
			, String durationInMinutes, List<SeatDTO> seat) {
		
		super(id,name,date,time,hall,description,genre,isPrimiery,durationInMinutes);
		this.seats = new ArrayList<SeatDTO>(seat);
	}
	
	
	
	
	
	
	private List<SeatDTO> seats;
	
}
