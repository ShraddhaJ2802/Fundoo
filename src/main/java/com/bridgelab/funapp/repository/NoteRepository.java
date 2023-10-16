package com.bridgelab.funapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
@Repository
public interface NoteRepository extends JpaRepository<Notes , Long>{

	//find all notes data using user id
	
	@Query(value = "SELECT * from Notes_Data where Notes_Data.User_id = :id" , nativeQuery = true )
	Optional<Notes> findByUserId(long id);
	
	//find all notes data by label
	
	@Query(value = "Select  *  from NOTES_DATA , NOTES_LABELS  where  NOTES_DATA.ID =  NOTES_LABELS.NOTES_ID and LABELS  = :label" , nativeQuery = true)
	List<Notes> findNotesByLabel(String label);

	//find labels by noteid 
	
	@Query(value = "Select  *  from NOTES_LABELS , NOTES_DATA   where  NOTES_DATA . ID =  NOTES_LABELS .NOTES_ID  and NOTES_DATA . ID= :noteId" , nativeQuery = true)
	List<String> findLabelsByNoteId(long noteId);
	

}
