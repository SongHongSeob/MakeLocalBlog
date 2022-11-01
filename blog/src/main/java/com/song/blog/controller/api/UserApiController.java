package com.song.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.song.blog.config.auth.PrincipalDetail;
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
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		//	 여기서는 트랜잭션이 종료되어서 DB에 값은 변경이 되었지만
		// 세션값은 아직 변경이 되지 않은 상태이다.
		// 그러므로 세션값 변경 함수가 필요하다.
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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
