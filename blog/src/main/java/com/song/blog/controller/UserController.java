package com.song.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.blog.model.KaKaoProfile;
import com.song.blog.model.OAuthToken;
import com.song.blog.model.User;
import com.song.blog.service.UserService;

// 인증이 안된 사용자들이 출입할 수 있는  경로를 /auth/** 허용
// 그냥 주소가 /이면 index.jsp 허용
// static 이하에 있는 파일들 허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {		// @ResponseBody를 함수에 붙이면 Data를 리턴해주는 컨트롤러 함수
		
		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		// 라이브러리 종료
		// Retrofit2
		// OkHttp
		// RestTemplate
		
		String client_id = "05df34917b69540c9571e67bb69a7d0b";
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", client_id);
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		
		// Gson, Json Simple, ObjectMapper
				ObjectMapper objectMapper2 = new ObjectMapper();
				KaKaoProfile kaKaoProfile = null;
				try {
					kaKaoProfile = objectMapper2.readValue(response2.getBody(), KaKaoProfile.class);
				} catch (JsonMappingException e) {
					// TODO: handle exception
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				System.out.println("카카오 아이디(번호) : "+kaKaoProfile.getId());
				System.out.println("카카오 이메일 : "+kaKaoProfile.getKakao_account().getEmail());
				
				System.out.println("블로그서버 유저네임 : "+kaKaoProfile.getKakao_account().getEmail()+"_"+kaKaoProfile.getId());
				System.out.println("블로그서버 이메일 : "+kaKaoProfile.getKakao_account().getEmail());
				// UUID garbagePassword = UUID.randomUUID();		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
				System.out.println("블로그서버 패스워드 : "+ cosKey);
				
				User kakaoUser = User.builder()
						.username(kaKaoProfile.getKakao_account().getEmail()+"_"+kaKaoProfile.getId())
						.password(cosKey)
						.email(kaKaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
				
				// 가입자 혹은 비가입자 체크 처리
				User originUser = userService.회원찾기(kakaoUser.getUsername());
				
				if(originUser.getUsername() == null) {
					System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다....................!!");
					userService.회원가입(kakaoUser);
				}
				
				System.out.println("자동 로그인을 진행합니다.");
				//세션 등록
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
		return "redirect:/";
	}

}
