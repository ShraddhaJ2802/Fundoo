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

	//dependency injection of User Repository
	@Autowired
	private UserRepository userRepository;
	//dependency injection of jwt
	@Autowired
	private JwtToken jwtToken;
	//dependency injecting email service
	@Autowired
	private EmailService emailService;
	
	/* Purpose: this method is used to registration of the the user
	 *Pass the UserDTO  object i.e.user as parameter in method
	 *To check the unique email of user 
	 *AT time of registration send the email to user and  send the otp to the user through email
	 * returns proper response to the controllerr */ 
	
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
	
	/* Purpose: this method is used to To find all user 
	 * @return returns proper response  to controller */
	
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	/* Purpose: this method is used to find particular user
	 *  id this can specify which user id pass which can to find user
	 * returns proper response to controller  */
	
	public User getById(String id) {
		
		return userRepository.findById(id)
				.orElseThrow(() -> new UserCustomException("User with id:"+id+" not found"));
	}

	/* Purpose: this method is used to delete the user
	 * id this can specify which user id pass then it can delete user
	 * returns proper response to controller */
	
	public void deleteUser(String id) {
		User user_Data = getById(id);
		if(user_Data != null) {
			userRepository.deleteById(id);
			}	
	}

	/* Purpose: this method is used to modify the user data
	 * id this can specify which user id pass which can update user
	 * user pass  this is used for send json data which get from client
	 * returns proper response to controller */
	
	public User updateUser(String id, UserDTO user) {
	
		User user_Data = getById(id);
		if(user_Data != null) {
			user_Data.setName(user.getName());;
			user_Data.setEmail(user.getEmail());
			user_Data.setPassword(user.getPassword());
			return userRepository.save(user_Data);
		}
		
		return null;
	}

	/* Purpose: this method is used for login ,to check credential of the user
	 *  email and password to pass method argument using this to check credential and  create the jwt token for authorization
	 * returns proper response to controller*/
	
	public String logIn(String email, String password) {
		
		User user_Data = userRepository.findByEmailAndPassword(email , password );
		if(user_Data !=null)
		{
			return jwtToken.createToken(user_Data.getId());
		}
		return null;	
	}
	
	/*Purpose:This method is use for checking the user email unique , not allowing dulicate emails 
	 * It take email as parameter 
	 * it return proper response to service class of user at time of registration
	 */
	public User uniqueEmail(String email)
	{
		User user= userRepository.findByUniqueEmail(email);
		return user;
	}
	
	/*Purpose:this method is used for  verify the user and set the value of verify is true 
	 * It take  the argument email an otp which send on email at time of registration
	 * it return the proper response to  controller 
	 */
	public User verifyUser(String email, int otp) {
		
		    User user_Data = userRepository.findByOTP(email , otp );
			user_Data.setVerify(true);
			return userRepository.save(user_Data);		
	}
}
