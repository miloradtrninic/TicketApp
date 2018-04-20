package com.msmisa.TicketApp.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.beans.FanAd;
import com.msmisa.TicketApp.beans.Privilege;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.dao.user.MembershipDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dao.user.UserRoleDao;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.UserCreationDTO;
import com.msmisa.TicketApp.events.OnRegistrationCompleteEvent;
import com.msmisa.TicketApp.security.JwtAuthenticationRequest;
import com.msmisa.TicketApp.security.JwtAuthenticationResponse;
import com.msmisa.TicketApp.security.JwtTokenUtil;
import com.msmisa.TicketApp.security.UserDetailsCustom;

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

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private MessageSource messages;

	@Value("Authorization")
	private String tokenHeader;

	@Value("mySecret")
	private String secret;

	@RequestMapping(value="/login",
			method=RequestMethod.POST,
			consumes= {"application/json", "application/json;charset=UTF-8"},
			produces= {"application/json"})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		try{
			User user = userDao.getByUserName(authenticationRequest.getUsername());

			if(user != null) {
				if(user.isEnabled()) {
					UsernamePasswordAuthenticationToken tokenauth = new UsernamePasswordAuthenticationToken(
							authenticationRequest.getUsername(),
							authenticationRequest.getPassword());
					final Authentication authentication = authenticationManager.authenticate(tokenauth);
					SecurityContextHolder.getContext().setAuthentication(authentication);
					final UserDetails userDetails = myAppUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
					final String token = jwtTokenUtil.generateToken(userDetails);
					List<String> roles = user.getUserRoles().stream().map(r -> r.getName()).collect(Collectors.toList());

					return ResponseEntity.ok(new JwtAuthenticationResponse(user.getId(), token, roles, getUserPrivilegeNames(user)));
				} else {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messages.getMessage("auth.msg.accLocked", null, new Locale("en)")));
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (BadCredentialsException | UsernameNotFoundException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	private List<String> getUserRolesNames(User user){
		List<String> ret = new ArrayList<String>();

		for(UserRole role : user.getUserRoles()) {
			ret.add(role.getName());
		}

		return ret;
	}

	private List<String> getUserPrivilegeNames(User user) {
		List<String> ret = new ArrayList<String>();

		for(UserRole role : user.getUserRoles()) {
			for(Privilege p : role.getPrivileges()) {
				ret.add(p.getName());
			}
		}

		return ret;
	}

	@Secured("ROLE_ANONYMOUS")
	@PostMapping(value="/register",
				consumes = {"application/json","application/json;charset=UTF-8"},
				produces = {"application/json"})
	public ResponseEntity<?> register(@DTO(UserCreationDTO.class) User user, WebRequest request) {
		ResponseEntity<?> ret = null;
		User toRet = null;
		UserRole role = null;
		User userName = userDao.getByUserName(user.getUsername());
		User userEmail = userDao.getByEmail(user.getEmail());

		if(userName == null && userEmail == null && validateUserInfo(user)) {
			role = roleDao.get(3);							//Recimo da je 1 obican user (registered)
			user.setMembership(membershipDao.get(1));		//Recimo da 1 obicno clanstvo (bronza)
			user.setEnabled(false);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setUserRoles(new HashSet<UserRole>(Arrays.asList(role)));
			user.setBidList(new HashSet<Bid>());
			user.setFriendOf(new HashSet<User>());
			user.setFriendRequests(new HashSet<User>());
			user.setFriendRequestsSent(new HashSet<User>());
			user.setFriends(new HashSet<User>());
			user.setUserAds(new HashSet<FanAd>());

			try {
				String appUrl = request.getContextPath();
				eventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, request.getLocale(), user));
				toRet = userDao.insert(user);
				ret = ResponseEntity.ok(toRet);
			} catch(Exception e) {
				System.out.println("Greska pri registraciji!");
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering you. Please, try again.");
			}
		} else {
			String msg = null;
			if(userName != null)
				msg = "Username is already taken. Try again with another username.";
			else if(userEmail != null)
				msg = "User with this e-mail already exists.";
			ret = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(msg);
		}

		return ret;
	}

	@GetMapping(value="/registrationConfirm")
	public ResponseEntity<?> confirmRegistration(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
		System.out.println(token);
		ResponseEntity<?> ret = null;
		String username = jwtTokenUtil.getUsernameFromToken(token);

		Date exp = jwtTokenUtil.getExpirationDateFromToken(token);
		System.out.println("Username: " + username);

		Date now = new Date();
		System.out.println("Exp: " + exp.toString());
		System.out.println("Now" + now.toString());
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DATE, 1);
		now = cal.getTime();

		boolean isTokenValid = now.after(exp);

		if(!isTokenValid) {
			String msg = messages.getMessage("auth.msg.invalidToken", null, new Locale("en"));
			ret = ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(msg);
		} else {
			User user = userDao.getByUserName(username);
		    user.setEnabled(true);
		    userDao.update(user);
		    response.sendRedirect("http://localhost:4200/#/login");
		    ret = ResponseEntity.ok().body("Redirecting you to login...");
		}

		return ret;
	}

	private boolean validateUserInfo(User user) {
		return user.getUsername().length() > 2 && user.getPassword().length() >= 6
				&& user.getPhoneNo().length() >=9 && user.getPhoneNo().length() <= 10
				&& user.getLastname() != null && user.getName() != null && user.getEmail().indexOf('@') != -1;
	}
}
