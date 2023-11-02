package com.bridgelab.funapp.dto;

import java.util.List;

import com.bridgelab.funapp.model.Collabrator;
import com.bridgelab.funapp.model.Label;
import com.bridgelab.funapp.model.User;

import jakarta.validation.constraints.NotEmpty;

public class NoteDTO {

	@NotEmpty(message = "Title not be empty")
	public String title;
	@NotEmpty(message = "description not be empty")
	public String description;
	public List<Label> label;
	public List<Collabrator> colla;	
}
