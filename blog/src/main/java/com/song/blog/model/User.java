package com.song.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder	// 빌더 패턴!!!
//ORM : Java(및 다른언어) Object를 테이블에 매핑해주는 기술
@Entity	// User 클래스가 MySQL에 테이블이 생성이 된다.
// @DynamicInsert // insert시에 null인 필드를 제외시켜준다.
public class User {
	
	@Id	// Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;	// 시퀀스, auto_increment
	
	@Column(nullable = false, length = 100,unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100)	// 비밀번호를 해쉬로 변경해서 암호화를 진행하기 위해서 길이를 100으로 지정
	private String password;
	
	@Column(nullable = false,length = 50)
	private String email;
	
	//@ColumnDefault("'user'")					// @ColumnDefault() 가로 사이의 문자값을 줄때는 "" 안에 ''를 넣어주어야 한다
	// DB는 RoleType이라는게 없다.
	@Enumerated(EnumType.STRING)
	private RoleType role;	// Enum을 쓰는게 좋다 // ADMIN, USER
	
	private String oauth; // kakao,google 확인용
	
	@CreationTimestamp	// 시간이 자동으로 입력
	private Timestamp createDate;
	
	@CreationTimestamp
	private Timestamp updateDate;
}
