package com.msmisa.TicketApp.resources;

import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.msmisa.TicketApp.beans.Bid;
import com.msmisa.TicketApp.beans.FanAd;
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
	public User register(@DTO(UserCreationDTO.class) User user, WebRequest request) {
		User toRet = null;
		UserRole role = null;
		User userExists = userDao.getByUserName(user.getUsername());

		if(userExists == null) {
			role = roleDao.get(1);							//Recimo da je 1 obican user
			user.setMembership(membershipDao.get(1));		//Recimo da 1 obicno clanstvo
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
			} catch(Exception e) {
				System.out.println("Greska pri slanju registracionog e-maila.");
			}
		}
		
		
		return toRet;
	}
	
	/*@GetMapping(value="/registrationConfirm")
	public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
		String ret = null;
		Locale locale = request.getLocale();
		boolean isTokenValid = jwtTokenUtil.validateToken(token, userDetails)		//???????????????????
		
		if(!isTokenValid) {
			String msg = messages.getMessage("auth.message.invalidToken", null, locale);
			model.addAtribute("message", msg);
			ret = "redirect:/badUser.html?lang=" + locale.getLanguage();
		} else {
			User user = verificationToken.getUser();
		    Calendar cal = Calendar.getInstance();
		    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
		        String messageValue = messages.getMessage("auth.message.expired", null, locale);
		        model.addAttribute("message", messageValue);
		        ret = "redirect:/badUser.html?lang=" + locale.getLanguage();
		    } 
		     
		    user.setEnabled(true); 
		    service.saveRegisteredUser(user); 
		    ret = "redirect:/login.html?lang=" + request.getLocale().getLanguage();
		}
		
		return ret;
	}*/
}
