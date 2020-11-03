package com.mimi.Service;

import com.mimi.Dto.Party;
import com.mimi.Dto.PartyRequest;

public interface PartyService {
	// 모임 업데이트
	public Party save(Party party);

	// 모임 생성
	public Party createParty(PartyRequest party);

	// 모임 삭제
	public void deleteParty(Party party);

	// 모임 가져오기
	public Party getParty(String id);
}
