package com.song.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
// 시큐리티 사용시 아래 3개는 세트
@Configuration	// 빈등록 (IoC 관리)
@EnableWebSecurity	// 필터를 거는 것( 스프링 시큐리티가 활성화가 되어 있는데 어떤 설정을 해당 파일에서 하겠다.) // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean // IoC가 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()	// csrf 토큰 비활성화 (테스츠시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
	}
}
