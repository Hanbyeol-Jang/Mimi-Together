package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.PartyDao;
import com.mimi.Dto.Party;
import com.mimi.Dto.PartyRequest;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyDao partyDao;

	@Override
	public Party createParty(PartyRequest partyReq) {

		Party party = new Party();

		List<String> list = new ArrayList<>();
		list.add(partyReq.getUserID());

		party.setUserList(list);
		party.setPtName(partyReq.getPtName());

		return partyDao.save(party);
	}

	@Override
	public void deleteParty(Party party) {
		partyDao.deleteById(party.getId());
	}

	@Override
	public Party save(Party party) {

		return partyDao.save(party);
	}

	@Override
	public Party getParty(String id) {
		return null;
//		return partyDao.findById(id);
	}
}
