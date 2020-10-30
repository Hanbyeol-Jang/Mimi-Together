package com.mimi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.PartyDao;
import com.mimi.Dto.Party;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyDao partyDao;

	@Override
	public Party createParty(Party party) {
		return partyDao.save(party);
	}

	@Override
	public void deleteParty(Party party) {
		partyDao.deleteById(party.getId());
	}

	@Override
	public Optional<Party> getParty(String id) {
		return partyDao.findById(id);
	}
}
