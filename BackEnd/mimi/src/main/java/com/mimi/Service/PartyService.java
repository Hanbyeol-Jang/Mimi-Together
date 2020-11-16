package com.mimi.Service;

import java.util.List;
import java.util.Optional;

import com.mimi.Dto.Party;

public interface PartyService {
	// 모임 업데이트
	public Party save(Party party);
	// 모임 생성
	public Party createParty(Party party);

	// 모임 삭제
	public void deleteParty(Party party);

	// 모임 가져오기
	public Party getParty(String id);
}
