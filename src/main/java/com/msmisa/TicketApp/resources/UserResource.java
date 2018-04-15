package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dto.creation.UserCreationDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;

import ch.qos.logback.classic.net.SyslogAppender;


@RestController
@RequestMapping(value="/user")
public class UserResource extends AbstractController<User, Integer> {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping(value="{id}", produces = {"application/json"})
	public UserPreviewDTO getUser(@PathVariable("id") String id) {
		return convertToDto(userDao.get(Integer.parseInt(id)), UserPreviewDTO.class);
	}
	
	@GetMapping(produces= "application/json")
	public ResponseEntity<List<UserPreviewDTO>> getAll(@RequestHeader("Authorization") String token) {
		ResponseEntity<List<UserPreviewDTO>> ret = null;
		List<User> users = userDao.getAll(); 
		List<UserPreviewDTO> usersDTO = users.stream().map(u -> convertToDto(u, UserPreviewDTO.class)).collect(Collectors.toList());
		
		if(usersDTO.isEmpty())
			ret = new ResponseEntity<List<UserPreviewDTO>>(usersDTO, HttpStatus.NO_CONTENT);
		else 
			ret = new ResponseEntity<List<UserPreviewDTO>>(usersDTO, HttpStatus.OK);
		
		return ret;
	}
	
	@GetMapping(value="/friends/{id}", produces = {"application/json"})
	public ResponseEntity<Set<UserPreviewDTO>> getUsersFriends(@PathVariable("id") Integer id) {
		ResponseEntity<Set<UserPreviewDTO>> ret = null;
		User user = userDao.get(id);
		List<User> allUsers = userDao.getAll();
		Set<User> users = user.getFriends();
		Set<UserPreviewDTO> usersDTO = allUsers.stream()
											.filter(u -> users.contains(u) == true && u.getId() != user.getId())
											.map(u -> convertToDto(u, UserPreviewDTO.class)).collect(Collectors.toSet());
		
		if(usersDTO.isEmpty())
			ret = new ResponseEntity<Set<UserPreviewDTO>>(usersDTO, HttpStatus.NO_CONTENT);
		else
			ret = new ResponseEntity<Set<UserPreviewDTO>>(usersDTO, HttpStatus.OK);
		
		return ret;
	}
	
	@GetMapping(value="/notFriends/{id}", produces= {"application/json"})
	public ResponseEntity<Set<UserPreviewDTO>> getUnaddedFriends(@PathVariable("id") Integer id) {
		ResponseEntity<Set<UserPreviewDTO>> ret = null;
		User user = userDao.get(id);
		List<User> allUsers = userDao.getAll();
		Set<User> users = user.getFriends();
		Set<UserPreviewDTO> usersDTO = allUsers.stream()
									.filter(u -> users.contains(u) == false &&
												 user.getFriendRequests().contains(u) == false &&
												 u.getId() != user.getId())
									.map(u -> convertToDto(u, UserPreviewDTO.class)).collect(Collectors.toSet());

		if(usersDTO.isEmpty())
			ret = new ResponseEntity<Set<UserPreviewDTO>>(usersDTO, HttpStatus.NO_CONTENT);
		else
			ret = new ResponseEntity<Set<UserPreviewDTO>>(usersDTO, HttpStatus.OK);
		
		return ret;
	}
	
	@PostMapping(value="/addFriend/{id}", consumes= {"application/json"}, produces= {"application/json"})
	public ResponseEntity<UserPreviewDTO> sendFriendRequest(@PathVariable("id") Integer senderId, @RequestBody Integer targetId) {
		ResponseEntity<UserPreviewDTO> ret = null;
		User sender = userDao.get(senderId);
		User target = userDao.get(targetId);
		
		if(!sender.getFriends().contains(target) && !sender.getFriendRequestsSent().contains(target) && 
			!target.getFriendRequests().contains(sender) && !target.getFriends().contains(sender)) {
			sender.getFriendRequestsSent().add(target);
			target.getFriendRequests().add(sender);
			userDao.update(sender);
			userDao.update(target);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.OK);
		} else {
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
		}
		
		return ret;
	}
	
	@PostMapping(value="/removeFriend/{id}", consumes= {"application/json"}, produces= {"application/json"})
	public ResponseEntity<UserPreviewDTO> removeFriend(@PathVariable("id") Integer senderId, @RequestBody Integer targetId) {
		ResponseEntity<UserPreviewDTO> ret = null;
		User sender = userDao.get(senderId);
		User target = userDao.get(targetId);
		
		if(sender.getFriends().contains(target) && target.getFriends().contains(sender)) {
			sender.getFriendRequestsSent().remove(target);
			sender.getFriends().remove(target);
			target.getFriendRequests().remove(sender);
			target.getFriends().remove(sender);
			userDao.update(sender);
			userDao.update(target);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.OK);
		} else if(sender.getFriendRequestsSent().contains(target)) {
			sender.getFriendRequestsSent().remove(target);
			userDao.update(sender);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.OK);
		} else {
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
		}
		
		return ret;
	}
	
	@GetMapping(value="/acceptFriendship/{id}", produces= {"application/json"})
	public ResponseEntity<UserPreviewDTO> acceptFriendRequest(@PathVariable("id") Integer accepterId, 
															  @RequestBody Integer requestSenderId) {
		ResponseEntity<UserPreviewDTO> ret = null;
		User sender = userDao.get(requestSenderId);
		User target = userDao.get(accepterId);
		
		try {
			target.getFriends().add(sender);
			target.getFriendRequests().remove(sender);
			sender.getFriends().add(target);
			sender.getFriendRequestsSent().remove(target);
			userDao.update(sender);
			userDao.update(target);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e ) {
			System.out.println("Greska pri prihvatanju prijatelja");
			e.printStackTrace();
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
		}
		
		return ret;
	}
	
	@GetMapping(value="/declineFriendship/{id}", produces= {"application/json"})
	public ResponseEntity<UserPreviewDTO> declineFriendRequest(@PathVariable("id") Integer declinerId, 
			  @RequestBody Integer requestSenderId) {
		ResponseEntity<UserPreviewDTO> ret = null;
		User sender = userDao.get(requestSenderId);
		User target = userDao.get(declinerId);
		
		try {
			target.getFriendRequests().remove(sender);
			sender.getFriendRequestsSent().remove(target);
			userDao.update(sender);
			userDao.update(target);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e ) {
			System.out.println("Greska pri prihvatanju prijatelja");
			e.printStackTrace();
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
		}
		
		return ret;
	}
	
	@PutMapping(value="/update", consumes= {"application/json"}, produces= {"application/json"})
	public ResponseEntity<?> updateInfo(@RequestBody UserPreviewDTO update) {
		ResponseEntity<?> ret = null;
		
		try {
			User u = userDao.get(update.getId());
			u.setEmail(update.getEmail());
			u.setName(update.getName());
			u.setLastname(update.getLastname());
			u.setPhoneNo(update.getPhoneNo());
			u.setCity(update.getCity());
			
			User toRet = userDao.update(u);
			
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(toRet, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("Ne moze update..");
			e.printStackTrace();
			ret = new ResponseEntity<String>("Error updating user info.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ret;
	}
	
	@PutMapping(value="/changePw/{id}", consumes= {"text/plain"}, produces= {"application/json"})
	public ResponseEntity<?> changePassword(@RequestBody String newPw, @PathVariable("id") String id) {
		ResponseEntity<?> ret = null;
		User u = userDao.get(Integer.parseInt(id));
		
		if(u != null) {
			u.setPassword(passwordEncoder.encode(newPw));
			userDao.update(u);
			ret =  new ResponseEntity<UserPreviewDTO>(convertToDto(userDao.update(u), UserPreviewDTO.class), HttpStatus.OK);
		} else {
			ret = new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
		}
		
		return ret;
	}
	
	@GetMapping(value="/getFriendRequests/{id}", produces= {"application/json"})
	public ResponseEntity<?> getFriendRequests(@PathVariable("id") String id) {
		ResponseEntity<?> ret = null;
		User u = userDao.get(Integer.parseInt(id));
		
		if(u != null) {
			List<UserPreviewDTO> usersDTO = u.getFriendRequests().stream()
					.map(us -> convertToDto(us, UserPreviewDTO.class)).collect(Collectors.toList());
			
			ret =  new ResponseEntity<List<UserPreviewDTO>>(usersDTO, HttpStatus.OK);
		} else {
			ret = new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
		}
		
		return ret;
	}
}
