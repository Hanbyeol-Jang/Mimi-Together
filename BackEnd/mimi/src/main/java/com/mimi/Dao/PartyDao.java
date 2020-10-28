package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mimi.Dto.Party;

public interface PartyDao extends MongoRepository<Party, Object> {

}
