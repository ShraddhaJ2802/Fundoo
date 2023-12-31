package com.bridgelab.funapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelab.funapp.dto.ResponseDto;
import com.bridgelab.funapp.dto.UserDTO;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/api")
public class UserController {
	
	//dependency injection of User service
	@Autowired
	UserService userService;
	
	//To register validate user and url is /user/api/add
	/* Purpose: this method is used to register the user
	 * @RequestBody user  this is used for send json data which are get from user 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@PostMapping("/add")
	 public ResponseEntity<ResponseDto> registrationUser(@Valid @RequestBody UserDTO user)
	 {
		User userData = userService.registrationUser(user);
		if(userData == null)
		{
			ResponseDto respDto = new ResponseDto("Registration  unsuccessfull Plese Try once again and check your email" , userData);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.BAD_REQUEST);
		}	
		else
		{	
			ResponseDto respDto = new ResponseDto("Registration  successfully done" , userData);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.CREATED);
		}
	 }
	
	/* Purpose: this method is used to To find all user 
	 * @return returns proper response entity of showing proper response to the user */ 
	
	@GetMapping("/getalluser")
	public ResponseEntity<ResponseDto> getAllUser()
	{
		List<User> user = userService.getAllUser();
		ResponseDto respDto = new ResponseDto("To find all users" , user);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		
	}
	
	/* Purpose: this method is used to find particular user
	 * @PathVariable id this can specify which user id pass which can find user
	 * returns proper response entity of showing proper response to the user  */
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseDto> getById(@PathVariable String id)
	{
		User user = userService.getById(id);
		ResponseDto respDto = new ResponseDto("To find  user" , user);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	/* Purpose: this method is used to delete the user
	 * @PathVariable id this can specify which user id pass then it can delete user
	 * returns proper response entity of showing proper response to the user */
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable String id  )
	{
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/* Purpose: this method is used to modify the user data
	 * @PathVariable id this can specify which user id pass which can update user
	   @RequestBody  user pass  this is used for send json data which get from client
	 * returns proper response entity of showing proper response to the user */
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateUser(@PathVariable String id , @RequestBody UserDTO user )
	{
		User userData = userService.updateUser(id, user);
		ResponseDto respDto = new ResponseDto("Upadate user  successfully " , userData);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	/* Purpose: this method is used for login ,to check credential of the user
	 * @RequestParam email and password to pass method argument using this to check credential
	 * returns proper response entity of showing proper response to the user */
	
	@PostMapping("/login")
	public  ResponseEntity<ResponseDto> logIn(@RequestParam String email ,@RequestParam String password)
	{
		String token = userService.logIn(email , password);
		if(token != null)
		{
			ResponseDto respDto = new ResponseDto("User log in successfull" , token);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);
		}
		
		else {
			ResponseDto respDto = new ResponseDto("User email or password invalid", null);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.BAD_REQUEST);
		}
	}
	
	/* Purpose: this method is used for verify the user
	 * @RequestParam email and otp using this  verify user.
	 * returns proper response entity of showing proper response to the user */
	
	@PostMapping("/verifyuser")
	public  ResponseEntity<ResponseDto> verifyUser(@RequestParam String email ,@RequestParam int otp)
	{
		User userData = userService.verifyUser(email , otp);
			ResponseDto respDto = new ResponseDto("User is verify" , userData);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);	
				//userService.logIn(id);
	}
}
