package com.bridgelab.funapp.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelab.funapp.model.User;
@Repository
public interface UserRepository extends MongoRepository<User ,String>{

	@Query("{'email':?0 , 'password':?1}")
	User findByEmailAndPassword(String email, String password);

	@Query("{'email':?0}")
	User findByUniqueEmail(String email);
	
	@Query("{'email':?0 , 'otp':?1}")
	User findByOTP(String email, int otp);
	
	
/*@Query(value=" select *  from User_Data where email = :email and password = :password " , nativeQuery = true)
User findByEmail(String email, String password);
@Query(value=" select *  from User_Data where email = :email and otp = :otp " , nativeQuery = true)
User findByOTP(String email, int otp);
@Query(value="select * from User_Data where email = :email"  , nativeQuery = true)
User findByUniqueEmail(String email);*/


}
