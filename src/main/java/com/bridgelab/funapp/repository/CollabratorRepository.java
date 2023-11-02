package com.bridgelab.funapp.repository;

import org.springframework.stereotype.Repository;

import com.bridgelab.funapp.model.Collabrator;
import com.bridgelab.funapp.model.Label;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@Repository

public interface CollabratorRepository extends MongoRepository<Collabrator , String>{
	@Query("{'User.id':?0 }")
	Optional<Label> findByUserId(String id);

}
