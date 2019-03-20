package com.example.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class HomePageKinoDto {
	
	private String name;
	private String addrees;
	private int numberOfHalls;
	private int rating;
	
}
