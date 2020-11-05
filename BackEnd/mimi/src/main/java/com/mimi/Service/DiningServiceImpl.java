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
	public List<Dining> getDiningByBoss(String boID) {

		
		List<Dining> diningList = diningDao.findAll();
		System.out.println(diningList);
		List<Dining> list = new ArrayList<>();

		for (int i = 0; i < diningList.size(); i++) {
			System.out.println(i);
			for(int j = 0;j<diningList.get(i).getStoreList().size();j++) {
				System.out.println(i+","+j);
				String temp = diningList.get(i).getStoreList().get(j).getBoID();
				System.out.println(temp);
				if (temp.equals(null)) {
					continue;
				}

				if (diningList.get(i).getStoreList().get(j).getBoID().equals(boID)) {
					list.add(diningList.get(i));
				}
			}
			
		}

		return list;
	}

}
