package com.bridgelab.funapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelab.funapp.model.User;
@Repository
public interface UserRepository extends JpaRepository<User ,Long>{
@Query(value=" select *  from User_Data where email = :email and password = :password " , nativeQuery = true)
User findByEmail(String email, String password);
@Query(value=" select *  from User_Data where email = :email and otp = :otp " , nativeQuery = true)
User findByOTP(String email, int otp);
@Query(value="select * from User_Data where email = :email"  , nativeQuery = true)
User findByUniqueEmail(String email);


}
