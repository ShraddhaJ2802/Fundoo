package com.bridgelab.funapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.bridgelab.funapp.dto.NoteDTO;
import com.bridgelab.funapp.exception.EmailAlreadyExistsException;
import com.bridgelab.funapp.exception.FunToCustomException;
import com.bridgelab.funapp.exception.UserCustomException;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.repository.NoteRepository;
import com.bridgelab.funapp.repository.UserRepository;
import com.bridgelab.funapp.util.JwtToken;

import jakarta.validation.Valid;

@Service
public class NoteService {
	
	@Autowired
	private NoteRepository trashSystemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtToken jwtToken;

	/* Purpose: this method is used to create the note
	 * pass token this is used for authorized user to access and also to take email 
	 * add to note which can become collaborator
	 * pass note  this is used for send json data can be list of labels ,collaborator id which get from user 
	 * @return returns proper response to controller*/ 
	public Notes createNote(String token ,  NoteDTO note) {
		
		String id =jwtToken.decodeToken(token);
		User user_Data = userRepository.findById(id)
					.orElseThrow();
		 if( user_Data.getEmail()!=null)
		 {
			 throw new EmailAlreadyExistsException("Email already exists: " + user_Data.getEmail());
		 }
		Notes notes = new Notes(note , user_Data);
		return trashSystemRepository.save(notes);
	}
	
	/* Purpose: this method is used to find particular note
	 * pass the token this is used for authorized user to access this method
	 * pass the id this can specify which note id pass which can find note
	 * returns proper response to controller  */
	
	public Notes getById(String token, String id) {
		
		String userId =jwtToken.decodeToken(token);
		User user_Data = userRepository.findById(userId)
					.orElseThrow(() -> new UserCustomException("User with id not found " + userId));
		return trashSystemRepository.findById(id)
				.orElseThrow(() -> new FunToCustomException("Notes with id:"+id+" not found"));
	}
	
	/* Purpose: this method is used to delete particular note
	 * pass the token this is used for authorized user to access this method
	 * pass the id this can specify which note id pass which can delete note
	 * returns proper response to controller */
	
	public void deleteNotes(String token, String id) {
		
		Notes note_Data = getById(token ,id);
		if(note_Data != null) {
			trashSystemRepository.deleteById(id);
			}
		}
	
	/* Purpose: this method is used to modify the note data
	 * pass the token this is used for authorized user to access this method 
	 * pass the id this can specify which note id pass which can update note
	   pass the notes pass  this is used for send json data which get from user
	 * returns proper response to controller */
	
	public Notes updateNotes(String token, String id, NoteDTO note) {
		
		Notes note_Data = getById(token ,id);
		if(note_Data != null) {
			note_Data.setTitle(note.title);
			note_Data.setDescription(note.description);
			return trashSystemRepository.save(note_Data);
			}	
		return null;
		}
	
	/* Purpose: this method is used to to find all notes 
	 * @return returns proper response to controller */
	
	public List<Notes> getAllNotes() {
		
		return trashSystemRepository.findAll();
		}
	
	/* Purpose: this method is used to update the archived is to set  true
	 * pass the  token this is used for authorized user to access this method
	 * pass the id pass the note id for update archived.
	 * returns proper response to controller*/
	
	public Notes updateArchievd(String token, String id) {
		
		Notes note_Data = getById(token, id);
		if(note_Data != null) {
			note_Data.setArchievd(true);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}

	/* Purpose: this method is used to update the trash is to set  true
	 * pass the token this is used for authorized user to access this method
	 * pass the id pass the note id for update trash .
	 * returns proper response to controller */
	
	public Notes updateTrash(String token, String id) {
		
		Notes note_Data = getById(token, id);
		if(note_Data != null) {
			note_Data.setTrash(true);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}
	
	/* Purpose: this method is used to update the archived is to set  false
	 * pass the token this is used for authorized user to access this method
	 * pass the id pass the note id for update archived.
	 * returns proper response to controller */

	public Notes updateUnarchievd(String token,String id) {
		
		Notes note_Data = getById(token,id);
		if(note_Data != null) {
			note_Data.setArchievd(false);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}

	/* Purpose: this method is used to update the trash is to set  false
	 * pass token this is used for authorized user to access this method
	 * pass id pass the note id for update trash .
	 * returns proper response entity of showing proper response to the user*/

	public Notes updateUntrash(String token, String id) {
		
		Notes note_Data = getById(token, id);
		if(note_Data != null) {
			note_Data.setTrash(false);
			return trashSystemRepository.save(note_Data);
		}
		return null;
	}

	/* Purpose: this method is used for find the notes 
	 * pass the token this is used for authorized user to access this method and  to take the user id 
	 * from token  to find out notes
	 * returns proper response to controller */
	
	public Optional<Notes> getByUserId(String token) {
	
		String id =jwtToken.decodeToken(token);
		return trashSystemRepository.findByUserId(id);
		}

	/* Purpose: this method is used for find the notes 
	  pass label id passing the label id which note user want
	 * returns proper response to controller */
	
	public List<Notes> findByLabelId(String id) {
		
		return trashSystemRepository.findByLabelId(id);
	}

	/* Purpose: this method is used for find the labels 
	  pass noteId passing the note  id which label list user want
	 * returns proper response entity of showing proper response to the user*/
	
	public List<String> getLabelsByNoteId(String noteId) {
		
		List<String> ls=trashSystemRepository.findLabelsByNoteId(noteId);
		return ls;
	}
}