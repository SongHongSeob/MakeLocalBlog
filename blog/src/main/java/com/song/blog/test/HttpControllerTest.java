package com.song.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String Tag = "HttpControllerTest : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		
		// builder의 장점
		// 객체 생성할때 순서가 없이 진행할수 있다
		Member m = Member.builder().username("song").password("1234").email("yahoo505@naver.com").build();
		System.out.println(Tag+"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(Tag+"setter : "+m.getUsername());
		
		return "lombok test 완료";
	}
	
	// 인터넷 브라우저 요청은 get만 가능
	
	// http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) { // id=1&username=송홍섭&password=1234&email=yahoo505@naver.com ?뒤의 이 값을 넘기는 방식 
		return "get 요청 : "+m.getId()+" , "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")	// text/plain, application/json
	public String postTest(@RequestBody Member m) {	// MessageConverter (스프링부트)가 json으로 보낸 데이터를 변수에 맵핑해준다.
		return "post 요청 : "+m.getId()+" , "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+" , "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
}
