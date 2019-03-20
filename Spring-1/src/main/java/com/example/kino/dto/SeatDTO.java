package com.example.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString

public class SeatDTO {
	
	private int numberOfPlace;
	private String isReserved;
	
}
