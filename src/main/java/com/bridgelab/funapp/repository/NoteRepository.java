package com.bridgelab.funapp.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
@Repository
public interface NoteRepository extends MongoRepository<Notes , String>{

	List<Notes> findByLabelsLabelName(String labelName);
	
	@Query("{'User.id':?0 }")
	Optional<Notes> findByUserId(String id);
	
	@Query("{'noteId':?0 }")
	List<String> findLabelsByNoteId(String noteId);

	@Query("{'Labels.labelId':?0 }")
	List<Notes> findByLabelId(String id);

	//find all notes data using user id
	/*@Query(value = "SELECT * from Notes_Data where Notes_Data.User_id = :id" , nativeQuery = true )
	Optional<Notes> findByUserId(long id);
	//find all notes data by label
	@Query(value = "Select  *  from NOTES_DATA , NOTES_LABELS  where  NOTES_DATA.ID =  NOTES_LABELS.NOTES_ID and LABELS  = :label" , nativeQuery = true)
	List<Notes> findNotesByLabel(String label);
	//find labels by noteid 
	@Query(value = "Select  Labels from NOTES_LABELS , NOTES_DATA   where  NOTES_DATA . ID =  NOTES_LABELS .NOTES_ID  and NOTES_DATA . ID= :noteId" , nativeQuery = true)
	List<String> findLabelsByNoteId(long noteId);*/

}
