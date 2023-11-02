package com.bridgelab.funapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelab.funapp.dto.CollabratorDTO;
import com.bridgelab.funapp.dto.NoteDTO;
import com.bridgelab.funapp.exception.CollabratorCustomException;
import com.bridgelab.funapp.exception.EmailAlreadyExistsException;
import com.bridgelab.funapp.exception.LabelCustomeException;
import com.bridgelab.funapp.exception.UserCustomException;
import com.bridgelab.funapp.model.Collabrator;
import com.bridgelab.funapp.model.Label;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.repository.CollabratorRepository;
import com.bridgelab.funapp.repository.NoteRepository;
import com.bridgelab.funapp.repository.UserRepository;
import com.bridgelab.funapp.util.JwtToken;

@Service
public class CollabratorService {
	
	@Autowired
	private CollabratorRepository collabratorRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	private NoteRepository noteRepository;
	 
	/* Purpose: this method is used to create the collaborator
	 * pass token  is used for authorized user
	 * @return returns proper response to controller */ 
	
	public Collabrator createCollabrator(String token) {
		
		String id =jwtToken.decodeToken(token);	
		User user_Data = userRepository.findById(id)
				.orElseThrow(() -> new UserCustomException("User with id not found " + id));
		Collabrator collabratorData = new Collabrator(user_Data );
		return collabratorRepository.save(collabratorData);
		}
	
	/* Purpose: this method is used to to find all collaborator 
	 * @return returns proper response to controller */ 
	
	public List<Collabrator> getAllCollabratorList() {		
		return collabratorRepository.findAll();
	}

	 /* Purpose: this method is used to delete the collaborator
	   * pass the  token  is used for authorized user to access this method
	   * pass the id this can specify which collaborator id pass which can delete collaborator
	   * returns proper response to controller */
	
	public void deleteCollabrator(String token, String id) {
		Collabrator collabratorData = getCollabratorById(token, id);
		if(collabratorData !=null)
		{
			collabratorRepository.deleteById(id);
		}
	}
	
	/* Purpose: this method is used for find the collaborator 
	  * pass the  token this is used for authorized user to access this method and  to take the user id 
	  * from token  to find out collaborator using user id
	  * returns proper response entity of showing proper response to the user*/
	
	public Optional<Label> getByUserId(String token) {
		String id =jwtToken.decodeToken(token);
		return collabratorRepository.findByUserId(id);
	}

	 /* Purpose: this method is used to find particular collaborator
	 * pass the token this is used for authorized user to access this method
	 * pass the id this can specify which collaborator id pass which can find collaborator
	 * returns proper response to controller  */
	
	public Collabrator getCollabratorById(String token, String id) {
		
		String userId =jwtToken.decodeToken(token);
		User user_Data = userRepository.findById(userId)
					.orElseThrow(() -> new UserCustomException("User with id not found " + userId));
		return collabratorRepository.findById(id)
				.orElseThrow(() -> new CollabratorCustomException("collabrator with id:"+id+" not found"));
			}
	
	 /* Purpose: this method is used for add list of note in collaborator
  	* pass the  token this is used for authorized user to access this method 
  	* pass the collaborator id for to check collaborator is present or not  
  	* pass the  notes  is used for save the list of notes in collaborator 
  	* returns proper response to controller */

	public void enrollCollabratorInNotes(String token, Notes note,String collabratorId ) {
		
		String userId =jwtToken.decodeToken(token);
		User user_Data = userRepository.findById(userId)
					.orElseThrow();
		 if( user_Data.getEmail()!=null)
		 {
			 throw new EmailAlreadyExistsException("Email already exists: " + user_Data.getEmail());
		 }
		 Collabrator collabrator = collabratorRepository.findById(collabratorId)
				 .orElseThrow(() -> new CollabratorCustomException("collabrator not found with id: " + collabratorId));
		 collabrator.getNotes().add(note);
		 collabratorRepository.save(collabrator);
	}
}
