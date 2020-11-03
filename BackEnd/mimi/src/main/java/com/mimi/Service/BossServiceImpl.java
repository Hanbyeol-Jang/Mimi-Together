package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.BossDao;
import com.mimi.Dao.DiningDao;
import com.mimi.Dto.Boss;
import com.mimi.Dto.Dining;
import com.mimi.Dto.TenderInfo;

@Service
public class BossServiceImpl implements BossService {

	@Autowired
	private BossDao bossDao;

	@Autowired
	private DiningDao diningDao;

	@Override
	public Boss createBoss(Boss boss) {
		return bossDao.save(boss);
	}

	@Override
	public void deleteBoss(Boss boss) {
		bossDao.deleteById(boss.getId());
	}

	@Override
	public Optional<Boss> getBoss(String id) {
		return bossDao.findById(id);
	}

	@Override
	public List<Dining> getAllAuction(String addr, int status) {
		System.out.println("getAllAuction Service");

		List<Dining> list = diningDao.findByDnLocationContainingAndDnStatus(addr, status);

		return list;
	}

	@Override
	public Dining tender(String dnID, String boID, int price, String memo) {
		System.out.println("tender service");

		Dining dining = diningDao.findById(dnID).get();

		System.out.println(dining.toString());
		// dining 에 입찰 추가
		TenderInfo tenderInfo = new TenderInfo();
		tenderInfo.setBoID(boID);
		tenderInfo.setPrice(price);
		tenderInfo.setMemo(memo);

		ArrayList<TenderInfo> list = dining.getStoreList();

		list.add(tenderInfo);

		dining.setStoreList(list);

		diningDao.save(dining);

		// 사장님 자신의 입찰 추가
		Boss boss = bossDao.findById(boID).get();

		List<String> dnList = boss.getDiningList();

		dnList.add(dnID);

		boss.setDiningList(dnList);
		System.out.println("222");

		bossDao.save(boss);

		System.out.println("333");

		return dining;
	}
}
