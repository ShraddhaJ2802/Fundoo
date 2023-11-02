package com.bridgelab.funapp.controller;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.funapp.dto.CollabratorDTO;
import com.bridgelab.funapp.dto.LabelDTO;
import com.bridgelab.funapp.dto.NoteDTO;
import com.bridgelab.funapp.dto.ResponseDto;
import com.bridgelab.funapp.model.Collabrator;
import com.bridgelab.funapp.model.Label;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.service.CollabratorService;

@RestController
@RequestMapping("/collabrator/api")
public class CollabratorController {
	
	//dependency injection of collaborator service
	@Autowired
	private CollabratorService collabratorService;
	
	/* Purpose: this method is used to create the collaborator
	 * @RequestHeader token this is used for authorized user
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@PostMapping("/add")
	public ResponseEntity<ResponseDto> createCollabrator( @RequestHeader String token)
	 {
		Collabrator colla = collabratorService.createCollabrator(token);
		ResponseDto respDto = new ResponseDto("Creating collabrator successfull" , colla);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.CREATED);
	 }
	
	/* Purpose: this method is used to to find all collaborator 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	 @GetMapping("/getall")
	 public ResponseEntity<ResponseDto> getAllCollabratorList() 
	 {
		 List<Collabrator> collabratorList = collabratorService.getAllCollabratorList();
		 ResponseDto respDto = new ResponseDto("Find all collabrtor" , collabratorList);
		 return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	 }
	 
	 /* Purpose: this method is used to find particular collaborator
		 * @RequestHeader token this is used for authorized user to access this method
		 * @PathVariable id this can specify which collaborator id pass which can find collaborator
		 * returns proper response entity of showing proper response to the user  */
		 
	 @GetMapping("/getCollabratorById/{id}")
	 public ResponseEntity<ResponseDto> getCollabratorById(@RequestHeader String token, @PathVariable String id)
	 {
		 Collabrator collabratorData = collabratorService.getCollabratorById(token, id);
		 ResponseDto respDto = new ResponseDto("Find the collabrator ", collabratorData);
		 return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	  }
		
	 /* Purpose: this method is used for add list of note in collaborator
	  	* @RequestHeader token this is used for authorized user to access this method 
	  	* @PathVariable id this can specify the collaborator id 
	  	* @RequestBody  notes pass  this is used for save the list of notes in collaborator 
	  	* returns proper response entity of showing proper response to the user */
		
	 @PutMapping("/update/{id}")
	 public ResponseEntity<Void> updateCollabrator(@RequestHeader String token , @RequestBody Notes note , @PathVariable 							String id)
	 { 
		 collabratorService.enrollCollabratorInNotes(token ,note ,id);
		 return ResponseEntity.noContent().build();
	 }

	 /* Purpose: this method is used to delete the collaborator
	   * @RequestHeader token this is used for authorized user to access this method
	   * @PathVariable id this can specify which collaborator id pass which can delete collaborator
	   * returns proper response entity of showing proper response to the user */
		 
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<Void> deleteCollabrator(@RequestHeader String token, @PathVariable String id )
	 {
		 collabratorService.deleteCollabrator(token, id);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
	 
	 /* Purpose: this method is used for find the collaborator 
	  * @RequestHeader token this is used for authorized user to access this method and  to take the user id 
	  * from token  to find out collaborator
	  * returns proper response entity of showing proper response to the user*/
		
	 @GetMapping("/getbyUserId")
	 public Optional<Label> getByUserId(@RequestHeader String token)
	 {
		 return collabratorService.getByUserId(token);
	  }
}
