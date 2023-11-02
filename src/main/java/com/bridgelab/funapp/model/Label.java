package com.bridgelab.funapp.model;

import java.time.LocalDate;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelab.funapp.dto.LabelDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "Labels")
public class Label {

	@Id
	private String labelId;
	private String labelName;
	@DBRef
	private User user;
	
	public Label(LabelDTO labelDTO , User user)
	{
		this.labelName =labelDTO.label;
		this.user = user;
	}
}
