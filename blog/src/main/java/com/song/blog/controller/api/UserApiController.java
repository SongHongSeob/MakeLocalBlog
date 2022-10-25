package com.song.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.song.blog.dto.ResponseDto;
import com.song.blog.model.RoleType;
import com.song.blog.model.User;
import com.song.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	/*
	 * @Autowired private HttpSession session;
	 */
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("UserApiController : save 호출됨");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);		// 자바오브젝트를 JSON으로 변환해서 리턴 (Jackson 라이브러리)
	}
	
	
	// 전통적인 로그인 방법 사용
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user, HttpSession session){
	 * System.out.println("UserApiController : login 호출됨"); User principal =
	 * userService.로그인(user);
	 * 
	 * if(principal != null) { session.setAttribute("principal", principal); }
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); }
	 */
}
