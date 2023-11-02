package com.bridgelab.funapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelab.funapp.dto.LabelDTO;
import com.bridgelab.funapp.exception.LabelCustomeException;
import com.bridgelab.funapp.exception.UserCustomException;
import com.bridgelab.funapp.model.Label;
import com.bridgelab.funapp.model.User;
import com.bridgelab.funapp.repository.LabelRepository;
import com.bridgelab.funapp.repository.UserRepository;
import com.bridgelab.funapp.util.JwtToken;

@Service
public class LabelService {
	
	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private JwtToken jwtToken;
	
	/* Purpose: this method is used to create the label
	 * @RequestHeader token this is used for authorized user to access 
	 * @RequestBody label  this is used for send data  in json format and  list of notes also send which enter from user 
	 * @return returns proper response to controller */ 
	
	public Label createLabel(String token, LabelDTO label) {
		String userId =jwtToken.decodeToken(token);
		User user_Data = userRepository.findById(userId)
					.orElseThrow(() -> new UserCustomException("User with id not found " + userId));
		Label Label_Data =new Label(label , user_Data);
		return labelRepository.save(Label_Data);
	}
	
	/* Purpose: this method is used to To find all labels 
	 * @return returns proper  response to controller  */ 
	
	public List<Label> getAllLabel() {
		return labelRepository.findAll();
		}
	 /* Purpose: this method is used to delete label
	  * pass the token this is used for authorized user to access this method
	  * pass the  id this can specify which label id pass which can delete label
	  * returns proper response to controller */ 
	
	public void deleteLabel(String token, String id) {
		Label labelData = getLabelById(token, id);
		if(labelData !=null)
		{	
			labelRepository.deleteById(id);
			}
		}

	 /* Purpose: this method is used to find particular labels
	  * pass the  token  this is used for authorized user to access this method
	  * pass the id this can specify which label id pass which can find label
	  * returns proper response to controller  */
	
	public Label getLabelById(String token , String id) {
		
		String userId =jwtToken.decodeToken(token);
		User user_Data = userRepository.findById(userId)
					.orElseThrow(() -> new UserCustomException("User with id not found " + userId));
		return labelRepository.findById(id).orElseThrow(() -> new LabelCustomeException("Labels with id:"+id+" not found"));
	}

	/* Purpose: this method is used to modify particular label
	  * pass token this is used for authorized user to access this method 
	  * pass id this can specify which label id pass which can modified label
		pass notes pass  this is used for send json data which get from user
	  * returns proper response to controller */
	 
	public Label updateLabel(String token, String id, LabelDTO label) {
		
		Label labelData = getLabelById(token, id);
		if(labelData !=null)
		{
			labelData.setLabelName(label.getLabel());
			return labelRepository.save(labelData);
		}
		
		return null;
	}

	 /* Purpose: this method is used for find the labels 
	  * pass token this is used for authorized user to access this method and  to take the user id 
	  * from token and  to find  labels
	  * returns proper response to controller*/
	public Optional<Label> getByUserId(String token) {
		
		String id =jwtToken.decodeToken(token);
		return labelRepository.findByUserId(id);
	}
}