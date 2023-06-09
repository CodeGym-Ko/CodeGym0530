package com.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

import com.board.dto.UserVO;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// From 로그인 으로 
		UserVO userInfo = service.userinfo(username); //username --> 아이디
		if(userInfo == null) {
			throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
		}
		
		//SimpleGrantedAuthority : 여러개의 사용자 Role값을 받아서 권한을 인식하는 역할을 함
		List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();		
		SimpleGrantedAuthority grantedAuthority 
			= new SimpleGrantedAuthority(userInfo.getRole());
		grantedAuthorities.add(grantedAuthority);
		System.out.println(" +++ UserDetailsServiceImpl +++ grantedAuthorities : "+ grantedAuthorities);
		//User 생성자 인자 --> 아이디, 패스워드, 사용자롤들
		User user = new User(username, userInfo.getPassword(), grantedAuthorities);
		
		return user;
	}
}
