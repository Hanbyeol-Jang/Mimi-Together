package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mimi.Dto.Dining;
import com.mimi.Dto.TenderInfo;

public interface DiningService {

	// 경매 등록
	public Dining upload(Dining dining);

	// 경매 취소
	public void remove(Dining dining);

	// 경매 확인
	public Optional<Dining> getDining(String id);

	// 사장님 자신이 입찰한 경매정보 확인
	public List<TenderInfo> getDiningByBoss(String id, String boID);

}
