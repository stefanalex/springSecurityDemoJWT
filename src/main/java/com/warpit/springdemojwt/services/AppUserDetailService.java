package com.warpit.springdemojwt.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AppUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// call by spring to load user by username
		//load here the user
		return new User("springdemo", "{noop}demo", new ArrayList<>());
		//return new User("springdemo", "demo", new ArrayList<>());
	}

}
