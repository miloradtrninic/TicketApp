package com.msmisa.TicketApp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.User;
import com.msmisa.TicketApp.dao.user.UserDao;

@RestController
@RequestMapping(value="/user")
public class UserResource extends AbstractController<User, Integer> {

	@Autowired
	private UserDao userDao;
	
	@GetMapping(value="/profile")
	public String getUser() {
		return "Hello";
	}
}
