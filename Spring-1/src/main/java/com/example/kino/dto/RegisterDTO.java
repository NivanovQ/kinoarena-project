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
public class RegisterDTO {

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String GSM;
	private String password;
}
