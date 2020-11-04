package com.mimi.Service;

import java.util.List;

import com.mimi.Dto.Party;
import com.mimi.Dto.PartyRequest;

public interface PartyService {
	// 모임 업데이트
	public Party save(Party party);

	// 모임 생성
	public Party createParty(PartyRequest party);

	// 모임 초대
	public Party joinParty(String userId, String partyId);

	// 가입된 모임 목록
	public List<String> partyList(String userId);

	// 모임 삭제
	public void deleteParty(Party party);
	// 위치 업데이트
	public Party getParty(String id);
}
