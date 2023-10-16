package com.bridgelab.funapp.util;

//this class is used  for the exchange of information though
//they more commonly  use for security purpose
import org.springframework.stereotype.Component;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtToken {
	//A JWT token is divided into 3 parts namely – header, payload, and signature 
	public final String SECRET = "SHRADDHA";
	//To create the token
	public String createToken(long id){
	return JWT.create()
	.withClaim("id",id)
	.sign(Algorithm.HMAC256(SECRET));
	}
	public long decodeToken(String token){
	long id =0;
	if(token !=null){
	id = JWT.require(Algorithm.HMAC256(SECRET))
	.build().verify(token)
	.getClaim("id").asLong();
	}
	return id;
	}

}
