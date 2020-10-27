package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.Dto.User;

@Repository
public interface UserDao extends MongoRepository<User, Object> {

}
