package com.msmisa.TicketApp.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Membership;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.MembershipCreationDTO;
import com.msmisa.TicketApp.dto.update.MembershipUpdateDTO;

@RestController
@RequestMapping(value="/membership")
public class MembershipResource extends AbstractController<Membership, Integer> {
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Membership>> getAll(){
		List<Membership> list = getDao().getAll();
		if(list.isEmpty())
			return new ResponseEntity<List<Membership>>(list,HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<Membership>>(list,HttpStatus.OK);
	}
	
	@PostMapping(value="/new", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Membership addNew(@DTO(value=MembershipCreationDTO.class) Membership entity){
		return getDao().insert(entity);
	}
	
	@PutMapping(value="/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Membership update(@DTO(value=MembershipUpdateDTO.class) Membership entity){
		return getDao().update(entity);
	}
}
