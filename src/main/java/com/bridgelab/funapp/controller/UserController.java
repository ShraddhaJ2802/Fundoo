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
	
	@Autowired
	UserService userService;
	
	//To register validate user and url is /user/api/add
	
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
	
	//To find all users and url is /user/api/getalluser
	
	@GetMapping("/getalluser")
	public ResponseEntity<ResponseDto> getAllUser()
	{
		List<User> user = userService.getAllUser();
		ResponseDto respDto = new ResponseDto("To find all users" , user);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
		
	}
	
	//To find the user by id and url is user/api/get/{id} and pass the user id as parameter
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ResponseDto> getById(@PathVariable long id)
	{
		User user = userService.getById(id);
		ResponseDto respDto = new ResponseDto("To find  user" , user);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	//To delete the user and url is user/api/delete/{id} and pass the user id as parameter
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id  )
	{
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	//update the user and url is user/api/update/{id} and pass the user id as parameter 
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseDto> updateUser(@PathVariable long id , @RequestBody UserDTO user )
	{
		User userData = userService.updateUser(id, user);
		ResponseDto respDto = new ResponseDto("Upadate user  successfully " , userData);
		return new ResponseEntity<ResponseDto>(respDto , HttpStatus.FOUND);
	}
	
	// this api for login , to check the credential passing through email and password and url is user/api/login
	
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
	
	// to verify the user using through email and otp at time of user registration time and api url is user/api/verifyuser
	
	@PostMapping("/verifyuser")
	public  ResponseEntity<ResponseDto> verifyUser(@RequestParam String email ,@RequestParam int otp)
	{
		User userData = userService.verifyUser(email , otp);
			ResponseDto respDto = new ResponseDto("User is verify" , userData);
			return new ResponseEntity<ResponseDto>(respDto , HttpStatus.OK);	
				//userService.logIn(id);
	}
}
