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

import com.bridgelab.funapp.dto.LabelDTO;
import com.bridgelab.funapp.dto.ResponseDto;
import com.bridgelab.funapp.model.Label;
import com.bridgelab.funapp.model.Notes;
import com.bridgelab.funapp.service.LabelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/labels/api")
public class LabelController {
	
	//dependency injection of label service
	@Autowired
	private LabelService labelService;

	/* Purpose: this method is used to create the label
	 * @RequestHeader token this is used for authorized user to access 
	 * @RequestBody label  this is used for send data  in json format and  list of notes also send which enter from user 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@PostMapping("/save")
	public ResponseEntity<ResponseDto> createLabel(@RequestHeader String token , @Valid @RequestBody LabelDTO label) {
		Label labelData =labelService.createLabel(token ,label);
		ResponseDto respDto = new ResponseDto("Creating labels successfull" , labelData);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.CREATED);
		
	}
	
	/* Purpose: this method is used to To find all labels 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@GetMapping("/getall")
	public ResponseEntity<ResponseDto> getAllLabel() {
		List<Label> labelList = labelService.getAllLabel();
	    ResponseDto respDto = new ResponseDto("Find all lebels" , labelList);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	   }
	 
	 /* Purpose: this method is used to find particular labels
	  * @RequestHeader token this is used for authorized user to access this method
	  * @PathVariable id this can specify which label id pass which can find label
	  * returns proper response entity of showing proper response to the user  */
	 
	 @GetMapping("/getLabelById/{id}")
	 public ResponseEntity<ResponseDto> getLabelById(@RequestHeader String token, @PathVariable String id) {
		 Label labelData = labelService.getLabelById(token, id);
		 ResponseDto respDto = new ResponseDto("Find the label" , labelData);
		 return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		}
	 
	 /* Purpose: this method is used to modify particular label
	  * @RequestHeader token this is used for authorized user to access this method 
	  * @PathVariable id this can specify which label id pass which can modified label
		@RequestBody  notes pass  this is used for send json data which get from user
	  * returns proper response entity of showing proper response to the user */
	 
	 @PutMapping("/update/{id}")
	 public ResponseEntity<ResponseDto> updateLabel(@RequestHeader String token, @PathVariable String id , @RequestBody LabelDTO label )
	 {
		 Label labelData= labelService.updateLabel(token ,id,label);
		 ResponseDto respDto = new ResponseDto("Update the label" , labelData);
		 return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
		}
	 
	 /* Purpose: this method is used to delete label
	  * @RequestHeader token this is used for authorized user to access this method
	  * @PathVariable id this can specify which label id pass which can delete label
	  * returns proper response entity of showing proper response to the user */ 
	 
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<Void> deleteLabel(@RequestHeader String token, @PathVariable String id )
	 {
		 labelService.deleteLabel(token, id);
		 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	 }
	 
	 /* Purpose: this method is used for find the labels 
	  * @RequestHeader token this is used for authorized user to access this method and  to take the user id 
	  * from token  to find out labels
	  * returns proper response entity of showing proper response to the user */
		
	 @GetMapping("/getbyUserId")
	 public Optional<Label> getByUserId(@RequestHeader String token){
		 return labelService.getByUserId(token);
		}
}
