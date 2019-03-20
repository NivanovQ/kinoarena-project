package com.example.kino.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProjectionsDTO {
	private Long id;
	private String name;
	private LocalDate date;
	private LocalTime time;
	private int hall;
	private String description;
	private String genre;
	private boolean isPrimiery;
	private String durationInMinutes;
}
