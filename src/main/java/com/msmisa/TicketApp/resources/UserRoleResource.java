package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.UserRole;

@RestController
@RequestMapping(value="/userroles")
public class UserRoleResource extends AbstractController<UserRole, Integer> {
	
}
