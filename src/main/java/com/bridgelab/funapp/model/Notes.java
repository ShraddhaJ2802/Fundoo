package com.bridgelab.funapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bridgelab.funapp.dto.NoteDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="Notes_Data")
@Data
@NoArgsConstructor
public class Notes {

	@Id
	@GeneratedValue
	private long id;
	private String title;
	private String description;
	private boolean deleted;
	private boolean archievd;
	private boolean trash;
	
	@JsonFormat(pattern ="yyyy-MM-dd")
	private LocalDate date;
	//one user has many notes
	 @ManyToOne
	 private User user;
	 @ElementCollection
	 private List<String> labels ;
	 
	public Notes(NoteDTO trashDTO , User user) {
		
		this.title = trashDTO.title;
		this.description = trashDTO.description;
		this.deleted = false;
		this.date = LocalDate.now();
		this.archievd = false;
		this.trash = false;
		this.user = user;
		this.labels = trashDTO.label;
	}
}
