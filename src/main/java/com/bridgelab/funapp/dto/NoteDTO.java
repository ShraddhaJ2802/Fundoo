package com.bridgelab.funapp.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class NoteDTO {

	@NotEmpty(message = "Title not be empty")
	public String title;
	
	@NotEmpty(message = "description not be empty")
	public String description;
	public List<String> label;
	
}
