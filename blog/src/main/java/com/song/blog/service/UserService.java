package com.song.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.song.blog.model.RoleType;
import com.song.blog.model.User;
import com.song.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌(IoC를 해준다.)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();		// 원문 비밀번호
		String encPassword = encoder.encode(rawPassword);	//해쉬화된 비밀번호
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	/*
	 * @Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
	 * 유지) public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),
	 * user.getPassword()); }
	 */

}
