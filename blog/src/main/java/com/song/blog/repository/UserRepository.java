package com.song.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.song.blog.model.User;

// DAO
// 자동으로 bean등록이 된다.
// @Repository // 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer>{
	
	
}


//JPA Naming 쿼리 전략
	// Select * From user Where username = ? And password = ?;
	// User findByUsernameAndPassword(String username, String password);
	
	/*
	 * @Query(value = "Select * From user Where username = ? And password = ?",
	 * nativeQuery = true) User login(String username, String passwrod);
	 */
