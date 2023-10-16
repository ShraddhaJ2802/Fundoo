package com.bridgelab.funapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

	@NotEmpty(message = "User name cannot be null")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$",message = "User name Invalid ")
	private String name;
	
	@Email
	private String email;
	
	@NotEmpty(message = "Password cannot be null")
	@Pattern(regexp="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$" , message = "Password atleast 8 chracters ,atmost 20 characters and atleast one digit,one lower case letter ,one capital case letter and one special symbol")
	private String password;

}
