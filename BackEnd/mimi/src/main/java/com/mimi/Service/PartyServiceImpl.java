package com.mimi.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.PartyDao;
import com.mimi.Dao.UserDao;
import com.mimi.Dto.Party;
import com.mimi.Dto.PartyRequest;
import com.mimi.Dto.User;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyDao partyDao;

	@Autowired
	private UserDao userDao;

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
	public void joinParty(String userId, String partyId) {
		// 모임에 초대 됨
		Party party = partyDao.findById(partyId).get();
		
		List<String> list = party.getUserList();
		list.add(userId);

		party.setUserList(list);

		partyDao.save(party);

		// user 에 모임 추가
		User user = userDao.findById(userId).get();
		
		List<String> userList = user.getPartyList();
		userList.add(partyId);

		user.setPartyList(userList);

		userDao.save(user);
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
