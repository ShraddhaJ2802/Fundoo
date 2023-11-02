package com.bridgelab.funapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelab.funapp.dto.UserDTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "User")
public class User {
	
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private boolean verify;
	private int otp;
	
	public User(UserDTO userDTO ) {
		
		this.name = userDTO.getName();
		this.email = userDTO.getEmail();
		this.password =userDTO.getPassword();
		this.verify =false;
		
	}
}
