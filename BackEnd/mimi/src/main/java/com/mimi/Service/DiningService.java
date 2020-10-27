package com.mimi.Service;

import java.util.Optional;

import com.mimi.Dto.Dining;

public interface DiningService {

	// 경매 등록
	public Dining upload(Dining dining);

	// 경매 취소
	public void remove(Dining dining);

	// 경매 확인
	public Optional<Dining> getDining(String id);
}
