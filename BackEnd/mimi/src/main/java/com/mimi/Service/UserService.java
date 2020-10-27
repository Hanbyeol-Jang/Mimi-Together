package com.mimi.Service;

import java.util.Optional;

import com.mimi.Dto.User;

public interface UserService {

	// 회원 가입
	public User join(User user);

	// 회원 탈퇴
	public void deleteAccount(User user);

	// 회원 정보 가져오기
    public Optional<User> getUserinfo(int id);
}
