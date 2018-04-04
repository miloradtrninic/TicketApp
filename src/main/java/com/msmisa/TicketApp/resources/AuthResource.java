package com.msmisa.TicketApp.resources;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.UserCreationDTO;
import com.msmisa.TicketApp.security.JwtAuthenticationRequest;
import com.msmisa.TicketApp.security.JwtAuthenticationResponse;
import com.msmisa.TicketApp.security.JwtTokenUtil;
import com.msmisa.TicketApp.security.UserDetailsCustom;
import com.msmisa.TicketApp.security.UsersAuthService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService myAppUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDao userDao;

	@Value("Authorization")
	private String tokenHeader;

	@RequestMapping(value="/login", 
			method=RequestMethod.POST,
			consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		try{
			System.out.println("Pozvan login.");
			final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(),
					authenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final UserDetails userDetails = myAppUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);			
			return ResponseEntity.ok(new JwtAuthenticationResponse(token,(UserDetailsCustom)userDetails));
		} catch (BadCredentialsException | UsernameNotFoundException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping(value="/register")
	public User passwordCrypt(@DTO(UserCreationDTO.class) User user) {
		//User toRet = userDao.insert(user);
		return user;
	}
	
	@RequestMapping(value="/register", 
					method=RequestMethod.POST,
					consumes= {"application/json"})
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			System.out.println("Pozvan register.");
			return ResponseEntity.ok(dao.insert(user));
		} catch(HibernateException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
	}
	
	@RequestMapping(value="/password", 
			consumes=MediaType.TEXT_PLAIN_VALUE,
			method=RequestMethod.POST)
	public String passwordCrypt(@RequestBody String pass) {
		System.out.println("odabran pass " + pass);
		PasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.encode(pass);
	}
	
}
