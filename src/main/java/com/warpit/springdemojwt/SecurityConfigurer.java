package com.warpit.springdemojwt;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.warpit.springdemojwt.filters.JwtRequestFilter;
import com.warpit.springdemojwt.services.AppUserDetailService;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailService appUserDetailService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//set values to AuthenticationManagerBuilder
		auth.userDetailsService(appUserDetailService);
	}
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().
						anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS); //spring security dont manage sessions
		                                                                 //we are using jwt to make it stateless
		
		//if spring security doesn't create a session there should be someone to do it
		//we add our filter before UsernamePasswordAuthenticationFilter
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	


	@Override
	@Bean
	//the default bean doesn't exists any more in spring 2.0
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}


	/**
	 * to not hash any incoming password
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		// {noop}password
		//encoders.put("noop", org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance());
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
	}

}
