package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.DiningDao;
import com.mimi.Dto.Dining;
import com.mimi.Dto.TenderInfo;

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

	@Override
	public List<TenderInfo> getDiningByBoss(String id, String boID) {

		ArrayList<TenderInfo> diningList = diningDao.findById(id).get().getStoreList();

		List<TenderInfo> list = new ArrayList<>();

		for (int i = 0; i < diningList.size(); i++) {
			String temp = diningList.get(i).getBoID();
			if (temp.equals(null)) {
				continue;
			}

			if (diningList.get(i).getBoID().equals(boID)) {
				diningList.get(i).setDnID(id);
				list.add(diningList.get(i));
			}
		}

		return list;
	}

}
