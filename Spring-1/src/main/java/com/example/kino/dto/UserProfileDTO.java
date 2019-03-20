package com.example.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserProfileDTO {
	private String firstName;
	private String lastName;
	private String GSM;
	private String email;
	
	
}
