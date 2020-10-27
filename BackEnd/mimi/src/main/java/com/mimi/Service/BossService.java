package com.mimi.Service;

import java.util.Optional;

import com.mimi.Dto.Boss;

public interface BossService {
	// 사장님 등록
	public Boss createBoss(Boss boss);

	// 사장님 탈퇴
	public void deleteBoss(Boss boss);

	// 사장님 id 로 확인
	public Optional<Boss> getBoss(String id);
}
