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

import com.bridgelab.funapp.dto.NoteDTO;
import com.bridgelab.funapp.dto.ResponseDto;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.service.NoteService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes/api")
public class NoteController {
	
	// dependency injection of Note service
	@Autowired
	NoteService trashService;
	
	//To create note with the url are notes/api/add . Here  pass the token at time of login user 
	
	@PostMapping("/add")
	 public ResponseEntity<ResponseDto> createNote( @RequestHeader String token, @Valid  @RequestBody NoteDTO note )
	 {
		Notes notes = trashService.createNote(token,note);
		ResponseDto respDto = new ResponseDto("Creating notes successfull" , notes);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.CREATED);
	 }
	

	//To find all notes present in table with url notes/api/getall
	
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllNote()
	{
		List<Notes> noteList = trashService.getAllNotes();
		ResponseDto respDto = new ResponseDto("Find all notes" , noteList);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	//To find note using note id with url is notes/api/get/{id}. Pass the note id as  parameter
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseDto> getById(@RequestHeader String token , @PathVariable long id)
	{
		Notes note= trashService.getById(token,id);
		ResponseDto respDto = new ResponseDto("Find the note" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	//Delete note by note id with url is notes/api/delete/1. Pass the note id as the parameter
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteNotes(@RequestHeader String token, @PathVariable long id  )
	{
		trashService.deleteNotes(token,id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//Update the note with help of note id and url is notes/api/update/{id}.Pass the note id as the parameter
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateNotes(@RequestHeader String token, @PathVariable long id , @RequestBody NoteDTO notes )
	{
		Notes note = trashService.updateNotes(token,id, notes);
		ResponseDto respDto = new ResponseDto("Update the note" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	// to set the archeived is true and save it and url is notes/api/updatearchievd/{id} 
	//pass the note id as parameter.
	
	@PutMapping("/updatearchievd/{id}")
	public ResponseEntity<ResponseDto> updateArchievd(@RequestHeader String token, @PathVariable long id  )
	{
		Notes note = trashService.updateArchievd(token, id);
		ResponseDto respDto = new ResponseDto("Archieved are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	//to set the trash is true and url is notes/api/updatetrash/{id}
	//pass the note id as parameter
	
	@PutMapping("/updatetrash/{id}")
	public ResponseEntity<ResponseDto> updateTrash(@RequestHeader String token, @PathVariable long id  )
	{
		Notes note = trashService.updateTrash(token,id);
		ResponseDto respDto = new ResponseDto("Trash are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	//to set the archievd is false and url is notes/api/updateunarchievd/{id}.Pass the note id as parameter
	
	@PutMapping("/updateunarchievd/{id}")
	public ResponseEntity<ResponseDto> updateUnarchievd(@RequestHeader String token,@PathVariable long id  )
	{
		Notes note = trashService.updateUnarchievd(token,id);
		ResponseDto respDto = new ResponseDto("Unarchieved are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	//to set the trash is false and url is notes/api/updateuntrash/{id}.Pass the note id as parameter
	
	@PutMapping("/updateuntrash/{id}")
	public ResponseEntity<ResponseDto> updateUntrash(@RequestHeader String token,@PathVariable long id  )
	{
		Notes note = trashService.updateUntrash(token,id);
		ResponseDto respDto = new ResponseDto("Untrash are successfully done" , note);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
	}
	
	//To find the notes using user id and url is notes/api//getbyUserId and pass the jwt token 
	
	@GetMapping("/getbyUserId")
	public Optional<Notes> getByUserId(@RequestHeader String token)
	{
		return trashService.getByUserId(token);
	}
	
	//find note list by label wise and url is notes/api/displayNotes and pass the label name as parameter
	
	@GetMapping("/displayNotes/{label}")
	public ResponseEntity<ResponseDto> getNotesByLabel(@PathVariable String label)
	{
		List<Notes> notes =  trashService.getNotesByLabel(label);
		System.out.println(notes);
		ResponseDto respDto = new ResponseDto("Get Call for notes successfull" , notes);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		
	}
	
	@GetMapping("/displayLabel/{noteId}")
	public ResponseEntity<ResponseDto> getLabelsByNoteId(@PathVariable long noteId)
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
