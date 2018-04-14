package com.msmisa.TicketApp.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.dao.user.UserDao;
import com.msmisa.TicketApp.dao.user.UserRoleDao;
import com.msmisa.TicketApp.dto.preview.UserPreviewDTO;
import com.msmisa.TicketApp.dto.update.UserRolesUpdateDTO;
import com.msmisa.TicketApp.dto.update.UserUpdateStatusDTO;

@RestController
@RequestMapping(value="/user")
public class UserResource extends AbstractController<User, Integer> {
	
	@Autowired
	private UserRoleDao userRoleDao;
	
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
	
}
