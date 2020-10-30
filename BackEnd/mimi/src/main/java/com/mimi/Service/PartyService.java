package com.mimi.Service;

import java.util.Optional;

import com.mimi.Dto.Party;

public interface PartyService {

	// 모임 생성
	public Party createParty(Party party);

	// 모임 삭제
	public void deleteParty(Party party);

	// 모임 가져오기
	public Optional<Party> getParty(String id);
}
