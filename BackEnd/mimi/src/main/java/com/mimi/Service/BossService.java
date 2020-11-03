package com.mimi.Service;

import java.util.List;
import java.util.Optional;

import com.mimi.Dto.Boss;
import com.mimi.Dto.Dining;

public interface BossService {
	// 사장님 등록
	public Boss createBoss(Boss boss);

	// 사장님 탈퇴
	public void deleteBoss(Boss boss);

	// 사장님 id 로 확인
	public Optional<Boss> getBoss(String id);

	// 주소 경매 리스트 확인
	public List<Dining> getAllAuction(String addr, int status);

}
