package com.mimi.mimi.store.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mimi.mimi.store.Dto.Store;

public interface StoreDao extends MongoRepository<Store, Integer> {
	
}
