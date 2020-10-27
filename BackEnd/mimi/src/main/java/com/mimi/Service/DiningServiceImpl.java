package com.mimi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.DiningDao;
import com.mimi.Dto.Dining;

@Service
public class DiningServiceImpl implements DiningService {

	@Autowired
	private DiningDao diningDao;

	@Override
	public Dining upload(Dining dining) {
		return diningDao.save(dining);
	}

	@Override
	public void remove(Dining dining) {
		diningDao.deleteById(dining.getId());
	}

	@Override
	public Optional<Dining> getDining(String id) {
		return diningDao.findById(id);
	}

}
