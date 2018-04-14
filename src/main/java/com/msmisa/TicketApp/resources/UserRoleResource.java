package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.UserRole;
import com.msmisa.TicketApp.dto.preview.UserRolePreview;

@RestController
@RequestMapping(value="/userroles")
public class UserRoleResource extends AbstractController<UserRole, Integer> {
	
	@GetMapping
	public ResponseEntity<List<UserRolePreview>> getAll() {
		List<UserRole> userRoles = getDao().getAll();
		List<UserRolePreview> usersPreview = convertToDto(userRoles, UserRolePreview.class);
		if(userRoles.isEmpty())
			return new ResponseEntity<List<UserRolePreview>>(usersPreview, HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<UserRolePreview>>(usersPreview, HttpStatus.OK);
	}
	
}
