package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.Dto.Dining;

@Repository
public interface DiningDao extends MongoRepository<Dining, Object> {

}
