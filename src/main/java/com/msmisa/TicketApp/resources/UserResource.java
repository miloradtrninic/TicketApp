package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.User;

@RestController
@RequestMapping(value="/user")
public class UserResource extends AbstractController<User, Integer> {
	
}
