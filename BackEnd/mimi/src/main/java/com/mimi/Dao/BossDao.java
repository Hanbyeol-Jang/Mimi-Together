package com.mimi.Dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.Dto.Boss;

@Repository
public interface BossDao extends MongoRepository<Boss, Object> {

}
