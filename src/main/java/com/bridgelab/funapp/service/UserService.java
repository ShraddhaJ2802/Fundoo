package com.bridgelab.funapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelab.funapp.dto.ResponseDto;
import com.bridgelab.funapp.dto.UserDTO;

import com.bridgelab.funapp.exception.UserCustomException;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.repository.UserRepository;
import com.bridgelab.funapp.util.EmailService;
import com.bridgelab.funapp.util.JwtToken;

import jakarta.validation.Valid;
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtToken jwtToken;
	@Autowired
	private EmailService emailService;
	public User registrationUser( UserDTO user) {
	   User uniqueUser = uniqueEmail(user.getEmail());  
	   if(uniqueUser != null)
	   {
		  new UserCustomException("Enter unique email :");
	   }
	   else
	   {
		   User user_Data = new User(user);
		
		//OTP generation
		Random random = new Random();
		int otpValue =  100_000 + random.nextInt(900_000);
	    user_Data.setOtp(otpValue);
		emailService.sendEmail(user_Data.getEmail(), "Registration is successfully done:", "Welcome"+user_Data.getName()+"You are registrtion successfully\n Your otp is:"+user_Data.getOtp());
		return userRepository.save(user_Data);
	   }
	   return null;
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public User getById(long id) {
		// TODO Auto-generated method stub UserCustomException
		
		return userRepository.findById(id)
				.orElseThrow(() -> new UserCustomException("User with id:"+id+" not found"));
	}

	public void deleteUser(long id) {
		User user_Data = getById(id);
		if(user_Data != null) {
			userRepository.deleteById(id);
			}	
	}

	public User updateUser(long id, UserDTO user) {
		// TODO Auto-generated method stub
		User user_Data = getById(id);
		if(user_Data != null) {
			user_Data.setName(user.getName());;
			user_Data.setEmail(user.getEmail());
			user_Data.setPassword(user.getPassword());
			return userRepository.save(user_Data);
		}
		
		return null;
	}



	public String logIn(String email, String password) {
		// TODO Auto-generated method stub
		User user_Data = userRepository.findByEmail(email , password );
		
		if(user_Data !=null)
		{
			return jwtToken.createToken(user_Data.getId());
		}
		
		return null;
		
	}

	public User uniqueEmail(String email)
	{
		User user= userRepository.findByUniqueEmail(email);
		return user;
	}
	
	public User verifyUser(String email, int otp) {
		    User user_Data = userRepository.findByOTP(email , otp );
			user_Data.setVerify(true);
			return userRepository.save(user_Data);
		
	}
}
