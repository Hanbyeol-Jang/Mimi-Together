package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mimi.Dto.Review;

public interface ReviewDao extends MongoRepository<Review, Integer> {
	
}
