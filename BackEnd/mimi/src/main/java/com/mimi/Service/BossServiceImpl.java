package com.mimi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.BossDao;
import com.mimi.Dto.Boss;

@Service
public class BossServiceImpl implements BossService {

	@Autowired
	private BossDao bossDao;

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
}
