package com.warpit.springdemojwt;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.warpit.springdemojwt.domain.AuthenticationRequest;
import com.warpit.springdemojwt.domain.AuthenticationResponse;
import com.warpit.springdemojwt.services.AppUserDetailService;
import com.warpit.springdemojwt.util.JwtUtil;

@RestController
public class HelloResource {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AppUserDetailService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@RequestMapping("/hello")
	public String hello() {
		return "Hello World";
	}
	
	@PostMapping("/authenticate")
	public AuthenticationResponse createAutenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUserName());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return new AuthenticationResponse(jwt);
		
	}
	
	
	
	/**
	    * Exception handler if NoSuchElementException is thrown in this Controller
	    *
	    * @param ex exception
	    * @return Error message String.
	    */
	   @ResponseStatus(HttpStatus.NOT_FOUND)
	   @ExceptionHandler(NoSuchElementException.class)
	   public String return400(NoSuchElementException ex) {
	       return ex.getMessage();

	   }
}
