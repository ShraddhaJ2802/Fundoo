package com.bridgelab.funapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelab.funapp.dto.NoteDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Document(collection = "Notes")
public class Notes {

	@Id
	private String noteId;
	private String title;
	private String description;
	private boolean deleted;
	private boolean archievd;
	private boolean trash;
	
	@JsonFormat(pattern ="yyyy-MM-dd")
	private LocalDate date;
	//one user has many notes
	@DBRef
	private User user;
	@DBRef
	private List<Label> labels ;	
	@DBRef
	private List<Collabrator> collabrator; 
	
	public Notes(NoteDTO trashDTO ,User user ) {
		
		this.title = trashDTO.title;
		this.description = trashDTO.description;
		this.deleted = false;
		this.date = LocalDate.now();
		this.archievd = false;
		this.trash = false;
		this.user = user;
		this.labels = trashDTO.label;
		this.collabrator = trashDTO.colla;
	}
}
