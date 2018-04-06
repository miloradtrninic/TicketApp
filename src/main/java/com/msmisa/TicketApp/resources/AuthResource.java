package com.msmisa.TicketApp.resources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
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

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.dao.user.MembershipDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dao.user.UserRoleDao;
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
	private UserDao userDao;

	@Autowired
	private MembershipDao membershipDao;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService myAppUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserRoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("Authorization")
	private String tokenHeader;

	@RequestMapping(value="/login",
			method=RequestMethod.POST,
			consumes= {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		try{
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

	@PostMapping(value="/register",
				consumes = {"application/json","application/json;charset=UTF-8"},
				produces = {"application/json"})
	public User register(@DTO(UserCreationDTO.class) User user) {
		User toRet = null;
		UserRole role = null;
		User userExists = userDao.getByUserName(user.getUsername());

		if(userExists == null) {
			role = roleDao.get(1);							//Recimo da je 1 obican user
			user.setMembership(membershipDao.get(1));		//Recimo da 1 obicno clanstvo
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setUserRoles(new HashSet<UserRole>(Arrays.asList(role)));
			user.setBidList(new HashSet<Bid>());
			user.setFriendOf(new HashSet<User>());
			user.setFriendRequests(new HashSet<User>());
			user.setFriendRequestsSent(new HashSet<User>());
			user.setFriends(new HashSet<User>());
			user.setUserAds(new HashSet<FanAd>());
		}

		return toRet;
	}
}
