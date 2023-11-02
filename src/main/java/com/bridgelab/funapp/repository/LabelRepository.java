package com.bridgelab.funapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelab.funapp.model.Label;


@Repository
public interface LabelRepository extends MongoRepository<Label , String>{

	@Query("{'User.id':?0 }")
	Optional<Label> findByUserId(String id);

}
