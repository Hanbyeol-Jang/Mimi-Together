package com.mimi.mimi.store.Dao;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mimi.mimi.store.Dto.Recommand;

@Repository
public interface RecommandDao extends MongoRepository<Recommand, Object> {
	public List<Recommand> findByNameAndAddressLike(String name,String address);

	public List<Recommand> findByName(String name);

	public List<Recommand> findByUidAndAddressLike(String name, String address);

	public List<Recommand> findByUid(String name);
}
