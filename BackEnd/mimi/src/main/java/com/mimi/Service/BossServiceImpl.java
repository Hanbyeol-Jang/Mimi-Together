package com.mimi.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.BossDao;
import com.mimi.Dao.DiningDao;
import com.mimi.Dto.Boss;
import com.mimi.Dto.Dining;

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
	public List<Dining> getAllAuction(String addr) {
		System.out.println("getAllAuction Service");

		List<Dining> list = diningDao.findByDnLocationContaining(addr);

		return list;
	}
}
