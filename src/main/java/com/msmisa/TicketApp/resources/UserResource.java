package com.msmisa.TicketApp.resources;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Auditorium;
import com.msmisa.TicketApp.beans.Movie;
import com.msmisa.TicketApp.beans.Play;
import com.msmisa.TicketApp.beans.Projection;
import com.msmisa.TicketApp.beans.Theatre;
import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.beans.WatchedProjection;
import com.msmisa.TicketApp.dao.projection.ProjectionDao;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dao.user.UserRoleDao;
import com.msmisa.TicketApp.dto.preview.AuditoriumPreviewDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;
import com.msmisa.TicketApp.dto.update.UserRolesUpdateDTO;
import com.msmisa.TicketApp.dto.update.UserUpdateStatusDTO;
import com.msmisa.TicketApp.dto.creation.UserCreationDTO;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;

import ch.qos.logback.classic.net.SyslogAppender;


@RestController
@RequestMapping(value="/user")
public class UserResource extends AbstractController<User, Integer> {

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private ProjectionDao projDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping
	public ResponseEntity<List<UserPreviewDTO>> getAll() {
		List<User> users = getDao().getAll();
		List<UserPreviewDTO> usersPreview = convertToDto(users, UserPreviewDTO.class);
		if(users.isEmpty())
			return new ResponseEntity<List<UserPreviewDTO>>(usersPreview, HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<UserPreviewDTO>>(usersPreview, HttpStatus.OK);
	}

	@GetMapping(value="byrole")
	public ResponseEntity<List<UserPreviewDTO>> getByRole(@RequestParam("role") String role) {
		UserDao userDao = (UserDao) getDao();
		List<User> users = userDao.getByRole(role);
		List<UserPreviewDTO> usersPreview = convertToDto(users, UserPreviewDTO.class);
		if(users.isEmpty())
			return new ResponseEntity<List<UserPreviewDTO>>(usersPreview, HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<UserPreviewDTO>>(usersPreview, HttpStatus.OK);
	}

	@PostMapping(value="updaterole")
	public ResponseEntity<UserPreviewDTO> updateRole(@RequestBody UserRolesUpdateDTO userUpdate){
		User user = getDao().get(userUpdate.getUserID());
		user.getUserRoles().removeIf(r -> !userUpdate.getRoles().contains(r.getId()));

		List<Integer> left = user.getUserRoles()
				.stream()
				.map(r -> r.getId())
				.collect(Collectors.toList());
		List<Integer> toAdd = userUpdate.getRoles()
				.stream()
				.filter(rId -> !left.contains(rId))
				.collect(Collectors.toList());
		if(toAdd != null && !toAdd.isEmpty()) {
			List<UserRole> userRoles = userRoleDao.getAllIn(toAdd);
			user.getUserRoles().addAll(userRoles);
		}

		return new ResponseEntity<>(convertToDto(getDao().update(user), UserPreviewDTO.class), HttpStatus.OK);
	}

	@RequestMapping(value="activate/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserPreviewDTO> activate(@PathVariable("id") Integer id) {

		User user = getDao().get(id);
		user.setEnabled(true);
		try {
			getDao().update(user);
			return new ResponseEntity<UserPreviewDTO>(convertToDto(user, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<UserPreviewDTO>(convertToDto(user, UserPreviewDTO.class), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

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
		Set<UserPreviewDTO> usersDTO = new HashSet<UserPreviewDTO>();

		for(User u : allUsers) {
			boolean shouldAdd = false;
			for(User u2 : users) {
				if(u2.getId() == u.getId()) {
					System.out.println("Add1: "+u2.getEmail());
					System.out.println("Add2: " + u.getEmail());
					shouldAdd = true;
					break;
				}
			}
			for(User fr : user.getFriendRequests()) {
				if(fr.getId() == u.getId()) {
					shouldAdd = false;
					break;
				}
			}
			for(User frs : user.getFriendRequestsSent()) {
				if(frs.getId() == u.getId()) {
					shouldAdd = false;
					break;
				}
			}
			if(shouldAdd && user.getId() != u.getId()) {
				usersDTO.add(convertToDto(u, UserPreviewDTO.class));
				System.out.println("Pushing user: "+u.getEmail());
				System.out.println("User info: " + user.getEmail());
			}

		}

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
		Set<UserPreviewDTO> usersDTO = new HashSet<UserPreviewDTO>();

		for(User u : allUsers) {
			boolean shouldAdd = true;
			for(User u2 : users) {
				if(u2.getId() == u.getId()) {
					shouldAdd = false;
					break;
				}
			}
			for(User fr : user.getFriendRequests()) {
				if(fr.getId() == u.getId()) {
					shouldAdd = false;
					break;
				}
			}
			for(User frs : user.getFriendRequestsSent()) {
				if(frs.getId() == u.getId()) {
					shouldAdd = false;
					break;
				}
			}
			if(shouldAdd && user.getId() != u.getId())
				usersDTO.add(convertToDto(u, UserPreviewDTO.class));
		}


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
		boolean aa = sender.getFriends().contains(target) && target.getFriends().contains(sender);

		try{
			List<User> friendlist = new ArrayList<User>(sender.getFriends());
			for(int i=0; i<friendlist.size(); i++) {
				User req = friendlist.get(i);
				if(req.getId() == targetId) {
					friendlist.remove(i);
				}
			}
			sender.setFriends(new HashSet<User>(friendlist));

			List<User> requests = new ArrayList<User>(target.getFriends());
			for(int i=0; i<requests.size(); i++) {
				User req = requests.get(i);
				if(req.getId() == senderId) {
					requests.remove(i);
				}
			}
			target.setFriends(new HashSet<User>(requests));

			List<User> sentrequests = new ArrayList<User>(sender.getFriendRequestsSent());
			for(int i=0; i<sentrequests.size(); i++) {
				User req = sentrequests.get(i);
				if(req.getId() == targetId) {
					sentrequests.remove(i);
				}
			}
			sender.setFriendRequestsSent(new HashSet<User>(sentrequests));
			
			List<User> friendrequests = new ArrayList<User>(target.getFriendRequests());
			for(int i=0; i<friendrequests.size(); i++) {
				User req = friendrequests.get(i);
				if(req.getId() == senderId) {
					friendrequests.remove(i);
				}
			}
			target.setFriendRequests(new HashSet<User>(friendrequests));

			userDao.update(sender);
			userDao.update(target);

			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(target, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
		}

		return ret;
	}

	@PutMapping(value="/acceptFriendship/{id}", produces= {"application/json"})
	public ResponseEntity<UserPreviewDTO> acceptFriendRequest(@PathVariable("id") Integer accepterId,
															  @RequestBody Integer requestSenderId) {
		ResponseEntity<UserPreviewDTO> ret = null;
		User sender = userDao.get(requestSenderId);
		User target = userDao.get(accepterId);

		try {
			target.getFriends().add(sender);
			List<User> requests = new ArrayList<User>(target.getFriendRequests());
			for(int i=0; i<requests.size(); i++) {
				User req = requests.get(i);
				if(req.getId() == requestSenderId) {
					requests.remove(i);
				}
			}
			target.setFriendRequests(new HashSet<User>(requests));

			List<User> sentrequests = new ArrayList<User>(sender.getFriendRequestsSent());
			for(int i=0; i<sentrequests.size(); i++) {
				User req = sentrequests.get(i);
				if(req.getId() == accepterId) {
					sentrequests.remove(i);
				}
			}
			sender.setFriendRequestsSent(new HashSet<User>(sentrequests));
			sender.getFriends().add(target);
			
			userDao.update(sender);
			userDao.update(target);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(sender, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e ) {
			System.out.println("Greska pri prihvatanju prijatelja");
			e.printStackTrace();
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(sender, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
		}

		return ret;
	}

	@PutMapping(value="/declineFriendship/{id}", produces= {"application/json"})
	public ResponseEntity<UserPreviewDTO> declineFriendRequest(@PathVariable("id") Integer declinerId,
			  @RequestBody Integer requestSenderId) {
		ResponseEntity<UserPreviewDTO> ret = null;
		User sender = userDao.get(requestSenderId);
		User target = userDao.get(declinerId);

		try {
			List<User> requests = new ArrayList<User>(target.getFriendRequests());
			for(int i=0; i<requests.size(); i++) {
				User req = requests.get(i);
				if(req.getId() == requestSenderId) {
					requests.remove(i);
				}
			}
			target.setFriendRequests(new HashSet<User>(requests));

			List<User> sentrequests = new ArrayList<User>(sender.getFriendRequestsSent());
			for(int i=0; i<sentrequests.size(); i++) {
				User req = sentrequests.get(i);
				if(req.getId() == declinerId) {
					sentrequests.remove(i);
				}
			}
			sender.setFriendRequestsSent(new HashSet<User>(sentrequests));
			userDao.update(sender);
			userDao.update(target);
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(sender, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e ) {
			System.out.println("Greska pri prihvatanju prijatelja");
			e.printStackTrace();
			ret = new ResponseEntity<UserPreviewDTO>(convertToDto(sender, UserPreviewDTO.class), HttpStatus.NOT_ACCEPTABLE);
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
	@RequestMapping(value="ban/{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserPreviewDTO> ban(@PathVariable("id") Integer id) {

		User user = getDao().get(id);
		user.setEnabled(false);
		try {
			getDao().update(user);
			return new ResponseEntity<UserPreviewDTO>(convertToDto(user, UserPreviewDTO.class), HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<UserPreviewDTO>(convertToDto(user, UserPreviewDTO.class), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/getVisited/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWatched(@PathVariable("id") Integer id) {
		try{
			User u = userDao.get(id);
			List<Projection> projections = projDao.getWatchedBy(u.getUsername());
			Set<Auditorium> auditoriums = new HashSet<Auditorium>();
			
			for(Projection p : projections) {
				if(p instanceof Movie) {
					auditoriums.add(((Movie) p).getCinema());
				} else if (p instanceof Play) {
					auditoriums.add(((Play) p).getTheatre());
				}
			}
			
			Set<AuditoriumPreviewDTO> audPreview = auditoriums.stream().map(a -> modelMapper.map(a, AuditoriumPreviewDTO.class))
														.collect(Collectors.toSet());
			
			return new ResponseEntity<Set<AuditoriumPreviewDTO>>(audPreview, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error fetching visits.", HttpStatus.NO_CONTENT);
		}
	}

}
