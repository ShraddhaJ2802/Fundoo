package com.bridgelab.funapp.util;

//This class is use for sending the notifications, account verifications,
//or newsletters, integrating email services
//with help of JavaMailSender.

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

//@Component on the class you let Spring create a bean for you. 
//This way Spring can, for example, inject this bean on runtime when you need it
@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;
	public void sendEmail(String toEmail, String subject, String body)
	{
	SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
	simpleMailMessage.setTo(toEmail);
	simpleMailMessage.setText(body);
	simpleMailMessage.setSubject(subject);
	mailSender.send(simpleMailMessage);
	System.out.println("Mail sent Successfully!!");
	}

}
