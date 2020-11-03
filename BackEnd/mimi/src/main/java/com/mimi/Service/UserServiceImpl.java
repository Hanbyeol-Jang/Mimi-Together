package com.mimi.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mimi.Dao.UserDao;
import com.mimi.Dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean login(String id) {
		boolean checked = userDao.existsById(id);

		return checked;
	}

	@Override
	public void modifySurvey(String id) {
		User user = userDao.findById(id).get();
		user.setIsSurvey("true");
		userDao.save(user);
	}

	@Override
	public User join(User user) {
		return userDao.save(user);
	}

	@Override
	public void deleteAccount(User user) {
		userDao.deleteById(user.getId());
	}

	@Override
	public Optional<User> getUserinfo(String id) {
		return userDao.findById(id);
	}

	@Override
	public User update(User user) {
		return userDao.save(user);
	}

}
