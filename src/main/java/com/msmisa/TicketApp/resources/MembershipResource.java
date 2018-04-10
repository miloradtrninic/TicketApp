package com.msmisa.TicketApp.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msmisa.TicketApp.beans.Membership;
import com.msmisa.TicketApp.dto.DTO;
import com.msmisa.TicketApp.dto.creation.MembershipCreationDTO;

@RestController
@RequestMapping(value="/membership")
public class MembershipResource extends AbstractController<Membership, Integer> {
	
	public Membership addNew(@DTO(value=MembershipCreationDTO.class) Membership entity){
		return getDao().insert(entity);
	}
}
