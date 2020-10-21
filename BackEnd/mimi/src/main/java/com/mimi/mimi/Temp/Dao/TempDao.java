package com.mimi.mimi.Temp.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.mimi.Temp.Dto.Temp;

@Repository
public interface TempDao extends MongoRepository<Temp, String>{
	
}
