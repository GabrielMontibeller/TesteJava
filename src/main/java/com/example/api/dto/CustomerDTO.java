package com.example.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CustomerDTO {
	
	private Long id;
	@NotNull(message = "Insira um nome")
	private String name;
	@NotNull(message = "Insira um email")
	@Email
	private String email;

}
