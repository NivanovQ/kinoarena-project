package com.example.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TicketDTO {
	
	private String descprtion;
	private int price;
}
