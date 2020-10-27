package com.mimi.mimi.store.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.mimi.store.Dao.RecommandDao;
import com.mimi.mimi.store.Dao.StoreDao;
import com.mimi.mimi.store.Dto.Recommand;
import com.mimi.mimi.store.Dto.Store;

@Service
public class StoreService {
	@Autowired
	private StoreDao storeDao;
	@Autowired
	private RecommandDao recommandDao;
	
	public Store save(Store store) {
		storeDao.save(store);
		return store;
	}
	
	public Recommand save(Recommand recommand) {
		recommandDao.save(recommand);
		return recommand;
	}
	
	public List<Store> findAll() {
		return storeDao.findAll();
	}
	
	public List<Recommand> recom(String name, String address){
		address = "%"+address+"%";
//		return recommandDao.findByName("삼창교자");
		return recommandDao.findByUid(name);
//		return recommandDao.findByUidAndAddressLike(name, address);
	}
	
}
