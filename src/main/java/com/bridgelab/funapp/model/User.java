package com.bridgelab.funapp.model;

import com.bridgelab.funapp.dto.UserDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "User_Data")
@Data
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue
	private long id;
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
