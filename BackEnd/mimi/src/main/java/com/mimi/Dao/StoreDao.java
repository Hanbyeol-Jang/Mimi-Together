package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mimi.Dto.Store;

public interface StoreDao extends MongoRepository<Store, Integer> {
	
}
