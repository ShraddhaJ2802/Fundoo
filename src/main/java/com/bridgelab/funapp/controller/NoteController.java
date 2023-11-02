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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelab.funapp.dto.NoteDTO;
import com.bridgelab.funapp.dto.ResponseDto;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.service.NoteService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes/api")
public class NoteController {
	
	//dependency injection of Note service
	@Autowired
	NoteService trashService;
	
	/* Purpose: this method is used to create the note
	 * @RequestHeader token this is used for authorized user to access and also to take email 
	 * add to note which can become collaborator
	 * @RequestBody note  this is used for send json data can be list of labels ,collaborator id which get from user 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@PostMapping("/add")
	 public ResponseEntity<ResponseDto> createNote( @RequestHeader String token, @Valid  @RequestBody NoteDTO note )
	 {
		Notes notes = trashService.createNote(token,note);
		ResponseDto respDto = new ResponseDto("Creating notes successfull" , notes);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.CREATED);
	 }
	
	/* Purpose: this method is used to to find all notes 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllNote()
	{
		List<Notes> noteList = trashService.getAllNotes();
		ResponseDto respDto = new ResponseDto("Find all notes" , noteList);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	/* Purpose: this method is used to find particular note
	 * @RequestHeader token this is used for authorized user to access this method
	 * @PathVariable id this can specify which note id pass which can find note
	 * returns proper response entity of showing proper response to the user  */
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseDto> getById(@RequestHeader String token , @PathVariable String id)
	{
		Notes note= trashService.getById(token,id);
		ResponseDto respDto = new ResponseDto("Find the note" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	/* Purpose: this method is used to delete particular note
	 * @RequestHeader token this is used for authorized user to access this method
	 * @PathVariable id this can specify which note id pass which can delete note
	 * returns proper response entity of showing proper response to the user */
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteNotes(@RequestHeader String token, @PathVariable String id  )
	{
		trashService.deleteNotes(token,id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/* Purpose: this method is used to modify the note data
	 * @RequestHeader token this is used for authorized user to access this method 
	 * @PathVariable id this can specify which note id pass which can update note
	   @RequestBody  notes pass  this is used for send json data which get from user
	 * returns proper response entity of showing proper response to the user */
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateNotes(@RequestHeader String token, @PathVariable String id , @RequestBody NoteDTO 			notes )
	{
		Notes note = trashService.updateNotes(token,id, notes);
		ResponseDto respDto = new ResponseDto("Update the note" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	/* Purpose: this method is used to update the archived is to set  true
	 * @RequestHeader token this is used for authorized user to access this method
	 * @PathVariable id pass the note id for update archived.
	 * returns proper response entity of showing proper response to the user*/
	
	@PutMapping("/updatearchievd/{id}")
	public ResponseEntity<ResponseDto> updateArchievd(@RequestHeader String token, @PathVariable String id  )
	{
		Notes note = trashService.updateArchievd(token, id);
		ResponseDto respDto = new ResponseDto("Archieved are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	/* Purpose: this method is used to update the trash is to set  true
	 * @RequestHeader token this is used for authorized user to access this method
	 * @PathVariable id pass the note id for update trash .
	 * returns proper response entity of showing proper response to the user */
	
	@PutMapping("/updatetrash/{id}")
	public ResponseEntity<ResponseDto> updateTrash(@RequestHeader String token, @PathVariable String id  )
	{
		Notes note = trashService.updateTrash(token,id);
		ResponseDto respDto = new ResponseDto("Trash are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	/* Purpose: this method is used to update the archived is to set  false
	 * @RequestHeader token this is used for authorized user to access this method
	 * @PathVariable id pass the note id for update archived.
	 * returns proper response entity of showing proper response to the user */
	
	@PutMapping("/updateunarchievd/{id}")
	public ResponseEntity<ResponseDto> updateUnarchievd(@RequestHeader String token,@PathVariable String id  )
	{
		Notes note = trashService.updateUnarchievd(token,id);
		ResponseDto respDto = new ResponseDto("Unarchieved are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	/* Purpose: this method is used to update the trash is to set  false
	 * @RequestHeader token this is used for authorized user to access this method
	 * @PathVariable id pass the note id for update trash .
	 * returns proper response entity of showing proper response to the user*/
	
	@PutMapping("/updateuntrash/{id}")
	public ResponseEntity<ResponseDto> updateUntrash(@RequestHeader String token,@PathVariable String id  )
	{
		Notes note = trashService.updateUntrash(token,id);
		ResponseDto respDto = new ResponseDto("Untrash are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	/* Purpose: this method is used for find the notes 
	 * @RequestHeader token this is used for authorized user to access this method and  to take the user id 
	 * from token  to find out notes
	 * returns proper response entity of showing proper response to the user*/
	
	@GetMapping("/getbyUserId")
	public Optional<Notes> getByUserId(@RequestHeader String token)
	{
		return trashService.getByUserId(token);
	}
	
	/* Purpose: this method is used for find the notes 
	  @PathVariable label id passing the label id which note user want
	 * returns proper response entity of showing proper response to the user*/
		
	@GetMapping("/displayNotes/{labelid}")
	public ResponseEntity<ResponseDto> getNotesByLabel(@PathVariable String labelid)
	{
		List<Notes> notes =  trashService.findByLabelId(labelid);
		System.out.println(notes);
		ResponseDto respDto = new ResponseDto("Get Call for notes successfull" , notes);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		
	}
	
	/* Purpose: this method is used for find the labels 
	  @PathVariable noteId passing the note  id which label list user want
	 * returns proper response entity of showing proper response to the user*/
	
	@GetMapping("/displayLabel/{noteId}")
	public ResponseEntity<ResponseDto> getLabelsByNoteId(@PathVariable String noteId)
	{
		List<String> labelList =  trashService.getLabelsByNoteId(noteId);
		if(labelList == null)
		{
			ResponseDto respDto = new ResponseDto("Get Call for label successfull and theire is nothing" , labelList);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		}
		else
		{
			ResponseDto respDto = new ResponseDto("Get Call for label successfull" , labelList);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		}	
	}
}
