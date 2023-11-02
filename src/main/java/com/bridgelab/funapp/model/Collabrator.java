package com.bridgelab.funapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelab.funapp.dto.CollabratorDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Document(collection = "Collabrator")
public class Collabrator {

	@Id
	private String collabId;
	@JsonFormat(pattern ="yyyy-MM-dd")
	private LocalDate createdDate;
	@JsonFormat(pattern ="yyyy-MM-dd")
	private LocalDate updatedDate;
	private User user;
	@DBRef
	private List<Notes> notes ;

	public Collabrator(User user) {
		this.createdDate = LocalDate.now();
		this.updatedDate = LocalDate.now();
		this.user =user;
	}	
}
